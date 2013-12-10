package plan3.recruitment.backend.resources;

import com.google.common.base.Optional;
import com.yammer.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import plan3.recruitment.backend.model.Person;
import plan3.recruitment.backend.model.PersonStorage;

import java.util.List;

// TODO - check this: http://jdbi.org/sql_object_overview/
public class InMemoryPersonStorage extends AbstractDAO<Person> implements PersonStorage {

    public InMemoryPersonStorage(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Person> list() {
        return list(namedQuery("plan3.recruitment.backend.model.Person.findAll"));
    }

    @Override
    public Optional<Person> fetch(String email) {
        Person person = uniqueResult(criteria().add(Restrictions.eq("email", email)));
        return Optional.fromNullable(person);
    }

    @Override
    public void save(Person person) {
        persist(person);
    }

    @Override
    public boolean remove(Person person) {
        throw new UnsupportedOperationException("Not supported yet..");
    }
}
