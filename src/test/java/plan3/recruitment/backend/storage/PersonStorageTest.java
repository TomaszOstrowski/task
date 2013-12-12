package plan3.recruitment.backend.storage;

import org.junit.Before;
import org.junit.Test;
import plan3.recruitment.backend.model.Person;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;


//TODO: test sorting functionality
public class PersonStorageTest extends StorageTestBase {

    private PersonStorage personStorage;

    @Before
    public void setUp() throws Exception {
        personStorage = new InMemoryPersonStorage(sessionFactory);
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

/*
    @Test
    public void shouldRetrieveAllSavedEntities() {
        // given no Person objects saved


        // when
        List<Person> persons = personStorage.list();

        // then
        assertThat(persons).isEmpty();

    }*/
}
