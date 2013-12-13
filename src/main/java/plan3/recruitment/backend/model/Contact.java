package plan3.recruitment.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.Query;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Embeddable;
import java.net.URI;

@Embeddable
public class Contact {

    @Email
    @JsonProperty
    private String email;

    private Contact() {
    }

    @JsonCreator
    public Contact(@JsonProperty("email") String email) {
        this.email = email;
    }

    @JsonIgnore
    public Query setEmailAsQueryParam(Query query) {
        return query.setParameter("email", email);
    }

    public URI provideLocation() {
        return URI.create(email);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;

        Contact contact = (Contact) o;

        if (email != null ? !email.equals(contact.email) : contact.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "email='" + email + '\'' +
                '}';
    }
}
