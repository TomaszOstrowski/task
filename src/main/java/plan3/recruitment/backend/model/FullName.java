package plan3.recruitment.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Embeddable;

@Embeddable
public class FullName {

    @NotBlank
    @Length(max = 35)
    @JsonProperty
    private String firstname;

    @NotBlank
    @Length(max = 35)
    @JsonProperty
    private String lastname;

    private FullName() {
    }

    @JsonCreator
    public FullName(@JsonProperty("firstname") String firstname,
                    @JsonProperty("lastname") String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FullName)) return false;

        FullName fullName = (FullName) o;

        if (firstname != null ? !firstname.equals(fullName.firstname) : fullName.firstname != null) return false;
        if (lastname != null ? !lastname.equals(fullName.lastname) : fullName.lastname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstname != null ? firstname.hashCode() : 0;
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FullName{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
