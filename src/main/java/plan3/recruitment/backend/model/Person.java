package plan3.recruitment.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hibernate.criterion.Criterion;

import javax.persistence.*;
import javax.validation.Valid;
import java.net.URI;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Valid
    @JsonUnwrapped
    @JsonProperty
    private PersonDetails personDetails;

    public Person() {
    }

    @JsonCreator
    public Person(@JsonProperty("personDetails") PersonDetails personDetails) {
        this.personDetails = personDetails;
    }

    @JsonIgnore
    public Criterion getEmailEqRestriction() {
        return personDetails.getEmailEqRestriction();
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
        return new Person(new PersonDetails(new FullName(firstname, lastname), new Contact(email)));
    }
}
