package plan3.recruitment.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hibernate.Query;

import javax.persistence.*;
import javax.validation.Valid;
import java.net.URI;

@Entity
@NamedQueries({
        @NamedQuery(name="Person.listAllByLastnameInAscOrder",
                    query="from Person p order by p.personDetails.fullName.lastname"),

        @NamedQuery(name="Person.getByEmail",
                    query="from Person p where p.personDetails.contact.email =:email")
})
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Valid
    @JsonUnwrapped
    @JsonProperty
    private PersonDetails personDetails;

    @JsonCreator
    public Person(@JsonProperty("personDetails") PersonDetails personDetails) {
        this.personDetails = personDetails;
    }

    private Person() {
    }

    public Query provideEmailForQuery(Query query) {
        return personDetails.provideEmailForQuery(query);
    }

    public boolean hasNoIdSet() {
        return id == null;
    }

    public URI provideLocation() {
        return personDetails.provideLocation();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (!personDetails.equals(person.personDetails)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return personDetails.hashCode();
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", personDetails=" + personDetails +
                '}';
    }

    // DO NOT REMOVE THIS METHOD. But feel free to adjust to suit your needs.
    public static Person valueOf(final String firstname, final String lastname, final String email) {
        FullName fullName = new FullName(firstname, lastname);
        Contact contact = new Contact(email);
        return new Person(new PersonDetails(fullName, contact));
    }
}
