package plan3.recruitment.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hibernate.criterion.Criterion;

import javax.validation.Valid;
import java.net.URI;

public class PersonDetails {

    @Valid
    @JsonUnwrapped
    @JsonProperty
    private final FullName fullName;

    @Valid
    @JsonUnwrapped
    @JsonProperty
    private final Contact contact;

    @JsonCreator
    public PersonDetails(@JsonProperty("fullName") FullName fullName,
                         @JsonProperty("contact") Contact contact) {
        this.fullName = fullName;
        this.contact = contact;
    }

    @JsonIgnore
    public Criterion getEmailEqRestriction() {
        return contact.getEmailEqRestriction();
    }

    public URI provideLocation() {
        return contact.provideLocation();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonDetails)) return false;

        PersonDetails that = (PersonDetails) o;

        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fullName != null ? fullName.hashCode() : 0;
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PersonDetails{" +
                "fullName=" + fullName +
                ", contact=" + contact +
                '}';
    }
}
