package plan3.recruitment.backend.validators;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.ws.rs.WebApplicationException;

public class InputValidatorTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    private InputValidator inputValidator = new InputValidator();

    @Test
    public void shouldProcessValidEmail() {
        // given
        String validEmail = "qiuck@brown.fox";

        // when
        inputValidator.validateEmail(validEmail);

        // then no exception
    }

    @Test
    public void shouldThrowExceptionForInvalidEmail() {
        // given
        String invalidEmail = "sorry@";
        exception.expect(WebApplicationException.class);

        // when
        inputValidator.validateEmail(invalidEmail);

        // then exception
    }
}
