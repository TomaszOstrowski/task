package plan3.recruitment.backend.validators;

import org.hibernate.validator.internal.constraintvalidators.EmailValidator;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class InputValidator {

    private final EmailValidator emailValidator = new EmailValidator();

    public void validateEmail(String email) {
        if (!emailValidator.isValid(email, null)) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}
