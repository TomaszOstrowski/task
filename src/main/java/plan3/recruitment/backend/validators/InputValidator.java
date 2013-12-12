package plan3.recruitment.backend.validators;

import org.hibernate.validator.internal.constraintvalidators.EmailValidator;

public class InputValidator {

    private final EmailValidator emailValidator = new EmailValidator();

    public boolean isEmailValid(String email) {
        return emailValidator.isValid(email, null);
    }
}
