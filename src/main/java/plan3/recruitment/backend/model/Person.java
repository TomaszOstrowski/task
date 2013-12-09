package plan3.recruitment.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    public Person(String firstname, String lastname, String email) {
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

    // DO NOT REMOVE THIS METHOD. But feel free to adjust to suit your needs.
    public static Person valueOf(String firstname, String lastname, String email) {
        return new Person(firstname, lastname, email);
    }
}