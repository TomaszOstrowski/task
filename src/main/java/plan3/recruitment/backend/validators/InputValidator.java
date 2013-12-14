package plan3.recruitment.backend.validators;

import org.hibernate.validator.internal.constraintvalidators.EmailValidator;

import static org.apache.commons.lang.StringUtils.isBlank;

public class InputValidator {

    private final EmailValidator emailValidator = new EmailValidator();

    public boolean isEmailValid(String email) {
        if (isBlank(email)) {
            return false;
        }

        return emailValidator.isValid(email, null);
    }
}
