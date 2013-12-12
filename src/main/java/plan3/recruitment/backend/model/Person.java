package plan3.recruitment.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.net.URI;

import static com.google.common.base.Objects.toStringHelper;

@Entity
@Table(name = "Person")
public class Person {

    public Person() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Length(max = 35)
    @JsonProperty
    private String firstname;

    @NotBlank
    @Length(max = 35)
    @JsonProperty
    private String lastname;

    @Email
    @JsonProperty
    private String email;

    public Person(@JsonProperty("firstname") final String firstname,
                  @JsonProperty("lastname") final String lastname,
                  @JsonProperty("email") final String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    @JsonIgnore
    public Criterion getEmailEqRestriction() {
        return Restrictions.eq("email", email);
    }

    public boolean hasNoIdSet() {
        return id == null;
    }

    public URI provideLocation() {
        return URI.create(email);
    }

    @Override
    public String toString() {
        return toStringHelper(this).omitNullValues()
                .add("id", id)
                .add("firstname", firstname)
                .add("lastname", lastname)
                .add("email", email).toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Person person = (Person) other;

        return Objects.equal(email, person.email)
            && Objects.equal(firstname, person.firstname)
            && Objects.equal(lastname, person.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(firstname, lastname, email);
    }

    // DO NOT REMOVE THIS METHOD. But feel free to adjust to suit your needs.
    public static Person valueOf(final String firstname, final String lastname, final String email) {
        return new Person(firstname, lastname, email);
    }
}
