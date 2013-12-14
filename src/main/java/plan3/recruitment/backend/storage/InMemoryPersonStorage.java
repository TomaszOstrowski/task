package plan3.recruitment.backend.storage;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.yammer.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import plan3.recruitment.backend.model.Person;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class InMemoryPersonStorage extends AbstractDAO<Person> implements PersonStorage {

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryPersonStorage.class);
    private static final boolean DOES_NOT_EXIST = false;
    private static final boolean REMOVED = true;

    public InMemoryPersonStorage(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Person> list() {
        Query query = namedQuery("Person.listAllByLastnameInAscOrder");
        List<Person> persons = list(query);

        logEntitiesFound(persons);
        return persons;
    }

    private void logEntitiesFound(Iterable<Person> persons) {
        if (persons != null && LOG.isDebugEnabled()) {
            LOG.debug("Person entities found: " + Joiner.on("|").join(persons));
        }
    }

    @Override
    public Optional<Person> fetch(final String email) {
        Query query = namedQuery("Person.getByEmail");
        query.setParameter("email", email);
        Person person = uniqueResult(query);

        logEntityFound(person);
        return Optional.fromNullable(person);
    }

    private void logEntityFound(Person person) {
        if (person != null && LOG.isDebugEnabled()) {
            LOG.debug("Person entities found: " + person);
        }
    }

    @Override
    public void save(final Person person) {
        Optional<Person> personOptional = findPerson(person);
        if (personOptional.isPresent()) {
            remove(personOptional.get());
        }
        persist(person);
        logEntitySaved(person);
    }

    private Optional<Person> findPerson(final Person person) {
        Query query = namedQuery("Person.getByEmail");
        person.setEmailAsQueryParam(query);
        Optional<Person> personOptional = Optional.fromNullable(uniqueResult(query));

        logPersonPresent(personOptional);
        return personOptional;
    }

    private void logPersonPresent(Optional<Person> personOptional) {
        if (personOptional.isPresent()) {
            LOG.debug("Person was found in DB: " + personOptional.get());
        }
    }

    private void logEntitySaved(Person person) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Entities saved: " + person);
        }
    }

    @Override
    public boolean remove(final Person person) {
        Optional<Person> localPerson = Optional.of(checkNotNull(person));
        if (person.hasNoIdSet()) {
            localPerson = findPerson(person);
        }
        if (localPerson.isPresent()) {
            currentSession().delete(localPerson.get());
            currentSession().flush();
            return REMOVED;
        }
        return DOES_NOT_EXIST;
    }

}
