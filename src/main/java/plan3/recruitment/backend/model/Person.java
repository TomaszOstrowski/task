package plan3.recruitment.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;


import static com.google.common.base.Objects.toStringHelper;

public class Person {

    @NotEmpty
    @Length(max = 35)
    @JsonProperty
    private final String firstname;

    @NotEmpty
    @Length(max = 35)
    @JsonProperty
    private final String lastname;

    @Email
    @JsonProperty
    private final String email;

    public Person(@JsonProperty("firstname") final String firstname,
                  @JsonProperty("lastname") final String lastname,
                  @JsonProperty("email") final String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("firstname", firstname)
                .add("lastname", lastname)
                .add("email", email).toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Person person = (Person) other;

        return Objects.equal(this.email, person.email)
            && Objects.equal(this.firstname, person.firstname)
            && Objects.equal(this.lastname, person.lastname);
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
