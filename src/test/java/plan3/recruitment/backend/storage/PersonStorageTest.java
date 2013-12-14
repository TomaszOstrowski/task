package plan3.recruitment.backend.storage;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import plan3.recruitment.backend.model.Person;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PersonStorageTest extends StorageTestBase {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private PersonStorage personStorage;

    @Before
    public void setUp() throws Exception {
        personStorage = new InMemoryPersonStorage(sessionFactory);
    }

    @Test
    public void shouldNotInstantiateClassIfNullPassedDuringConstruction() {
        // given
        exception.expect(NullPointerException.class);

        // when
        new InMemoryPersonStorage(null);

        // then Exception is thrown
    }

    @Test
    public void shouldReturnEmptyList() {
        // given no Person objects saved

        // when
        List<Person> persons = personStorage.list();

        // then
        assertThat(persons).isEmpty();
    }

    @Test
    public void shouldSaveGivenEntity() {
        // given
        Person person = Person.valueOf("Hannibal", "Lecter", "drLecter@gmail.com");
        personStorage.save(person);

        // when
        List<Person> persons = personStorage.list();

        // then
        assertThat(persons).hasSize(1).contains(person);
    }

    @Test
    public void shouldSaveGivenNumberOfEntities() {
        // given
        Person person1 = Person.valueOf("Hannibal", "Lecter", "drLecter@gmail.com");
        Person person2 = Person.valueOf("Red", "Dragon", "dragon@red.com");
        Person person3 = Person.valueOf("Tomasz", "Ostrowski", "toos@ymail.com");
        personStorage.save(person1);
        personStorage.save(person2);
        personStorage.save(person3);

        // when
        List<Person> persons = personStorage.list();

        // then
        assertThat(persons).hasSize(3).contains(person1, person2, person3);
    }

    @Test
    public void shouldReturnStoredEntitiesSortedByLastnameInAscendingOrder() {
        // given
        Person personL = Person.valueOf("Hannibal", "Lecter", "drLecter@gmail.com");
        Person personF = Person.valueOf("Hannibal", "Fecter", "drFecter@gmail.com");
        Person personC = Person.valueOf("Hannibal", "Cecter", "drCecter@gmail.com");
        Person personH = Person.valueOf("Hannibal", "Hecter", "drHecter@gmail.com");
        Person personA = Person.valueOf("Hannibal", "Aecter", "drAecter@gmail.com");

        personStorage.save(personL);
        personStorage.save(personF);
        personStorage.save(personC);
        personStorage.save(personH);
        personStorage.save(personA);

        // when
        List<Person> persons = personStorage.list();

        // then
        assertThat(persons).hasSize(5).containsExactly(personA, personC, personF, personH, personL);
    }

    @Test
    public void shouldReplaceEntityIfEmailMatch() {
        // given
        String personEmail = "drLecter@gmail.com";
        Person personL = Person.valueOf("Hannibal", "Lecter", personEmail);
        Person personA = Person.valueOf("Hannibal", "Aecter", personEmail);
        personStorage.save(personL);
        personStorage.save(personA);

        // when
        List<Person> persons = personStorage.list();

        // then
        assertThat(persons).hasSize(1).containsExactly(personA);
    }

    @Test
    public void shouldFindStoredEntityByEmail() {
        // given
        Person person1 = Person.valueOf("Hannibal", "Lecter", "drLecter@gmail.com");
        String person2email = "dragon@red.com";
        Person person2 = Person.valueOf("Red", "Dragon", person2email);
        Person person3 = Person.valueOf("Tomasz", "Ostrowski", "toos@ymail.com");
        personStorage.save(person1);
        personStorage.save(person2);
        personStorage.save(person3);

        // when
        Optional<Person> person = personStorage.fetch(person2email);

        // then
        assertTrue("Person should be found in DB for this email: " + person2email, person.isPresent());
        assertThat(person.get()).isEqualTo(person2);
    }

    @Test
    public void shouldNotReturnEntityIfGivenEmailNotPresentInDB() {
        // given
        Person person1 = Person.valueOf("Hannibal", "Lecter", "drLecter@gmail.com");
        Person person2 = Person.valueOf("Red", "Dragon", "dragon@red.com");
        Person person3 = Person.valueOf("Tomasz", "Ostrowski", "toos@ymail.com");
        personStorage.save(person1);
        personStorage.save(person2);
        personStorage.save(person3);

        String someOtherEmail = "hipsters@codein.java";

        // when
        Optional<Person> person = personStorage.fetch(someOtherEmail);

        // then
        assertFalse("Person should not be found in DB for this email: " + someOtherEmail, person.isPresent());
    }

    @Test
    public void shouldRemoveEntityFromDB() {
        Person person = Person.valueOf("Hannibal", "Lecter", "drLecter@gmail.com");
        personStorage.save(person);

        // when
        boolean wasRemoved = personStorage.remove(person);

        // then
        assertTrue("Person should have been removed from DB.", wasRemoved);
        List<Person> persons = personStorage.list();
        assertThat(persons).isEmpty();
    }

    @Test
    public void shouldRemoveEntityFromDbEvenIfItHasNoIdSet() {
        Person person = Person.valueOf("Hannibal", "Lecter", "drLecter@gmail.com");
        personStorage.save(person);
        person = Person.valueOf("Hannibal", "Lecter", "drLecter@gmail.com");

        // when
        boolean wasRemoved = personStorage.remove(person);

        // then
        assertTrue("Person should have been removed from DB.", wasRemoved);
        List<Person> persons = personStorage.list();
        assertThat(persons).isEmpty();
    }

    @Test
    public void shouldNotRemoveEntityIfItsNotInDB() {
        Person person1 = Person.valueOf("Hannibal", "Lecter", "drLecter@gmail.com");
        Person person2 = Person.valueOf("Tomasz", "Ostrowski", "toos@ymail.com");
        personStorage.save(person1);

        // when
        boolean wasRemoved = personStorage.remove(person2);

        // then
        assertFalse("Nothing should be removed from DB.", wasRemoved);
        List<Person> persons = personStorage.list();
        assertThat(persons).hasSize(1).contains(person1);
    }
}
