package plan3.recruitment.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hibernate.Query;

import javax.persistence.*;
import javax.validation.Valid;
import java.net.URI;

@Entity
@Table(name = "Person")
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
    private final PersonDetails personDetails;

    @JsonCreator
    public Person(@JsonProperty("personDetails") PersonDetails personDetails) {
        this.personDetails = personDetails;
    }

    public Query setEmailAsQueryParam(Query query) {
        return personDetails.setEmailAsQueryParam(query);
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

        if (id != null ? !id.equals(person.id) : person.id != null) return false;
        if (personDetails != null ? !personDetails.equals(person.personDetails) : person.personDetails != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (personDetails != null ? personDetails.hashCode() : 0);
        return result;
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
