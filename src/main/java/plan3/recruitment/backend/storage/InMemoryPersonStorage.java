package plan3.recruitment.backend.storage;

import com.google.common.base.Optional;
import com.yammer.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import plan3.recruitment.backend.model.Person;

import java.util.List;

public class InMemoryPersonStorage extends AbstractDAO<Person> implements PersonStorage {

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryPersonStorage.class);
    private static final boolean DOES_NOT_EXIST = false;

    public InMemoryPersonStorage(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Person> list() {
        return list(criteria().addOrder(Order.asc("lastname")));
    }

    @Override
    public Optional<Person> fetch(final String email) {
        Criteria byEmail = criteria().add(Restrictions.eq("email", email));
        Person person = uniqueResult(byEmail);
        return Optional.fromNullable(person);
    }

    @Override
    public void save(final Person person) {
        Optional<Person> personOptional = findPerson(person);
        if (personOptional.isPresent()) {
            remove(personOptional.get());
        }
        persist(person);
    }

    private Optional<Person> findPerson(final Person person) {
        LOG.debug("Looking for following person in DB: " + person);
        Criteria byEmail = criteria().add(person.getEmailEqRestriction());
        Optional<Person> personOptional = Optional.fromNullable(uniqueResult(byEmail));

        if (personOptional.isPresent()) {
            LOG.debug("Person was found in DB: " + personOptional.get());
        }

        return personOptional;
    }

    @Override
    public boolean remove(final Person person) {
        Optional<Person> localPerson = Optional.of(person);
        if (person.hasNoIdSet()) {
            localPerson = findPerson(person);
        }
        if (localPerson.isPresent()) {
            currentSession().delete(localPerson.get());
            currentSession().flush();
        }
        return DOES_NOT_EXIST;
    }

}
