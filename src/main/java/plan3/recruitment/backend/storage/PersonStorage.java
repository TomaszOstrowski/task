package plan3.recruitment.backend.storage;

import com.google.common.base.Optional;
import plan3.recruitment.backend.model.Person;

import java.util.List;

public interface PersonStorage {

    /**
     * Return an {@link Optional} wrapping the {@link plan3.recruitment.backend.model.Person} with the supplied email address or an
     * {@link Optional#absent()} if the persons doesn't exist in this {@link PersonStorage}.
     */
    public Optional<Person> fetch(final String email);

    /**
     * Add the given {@link Person} to this {@link PersonStorage}
     */
    public void save(final Person person);

    /**
     * Remove the given {@link Person}. Return {@code true} if the {@link Person} existed and was removed, {@code false}
     * if the person didn't exist
     */
    public boolean remove(final Person person);

    /**
     * Return all {@link Person}s in this {@link PersonStorage}
     */
    public List<Person> list();
}