package plan3.recruitment.backend.validators;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InputValidatorTest {

    private InputValidator inputValidator = new InputValidator();

    @Test
    public void shouldProcessValidEmail() {
        // given
        String validEmail = "qiuck@brown.fox";

        // when
        boolean result = inputValidator.isEmailValid(validEmail);

        // then
        assertTrue(result);
    }

    @Test
    public void shouldThrowExceptionForInvalidEmail() {
        // given
        String invalidEmail = "sorry@";

        // when
        boolean result = inputValidator.isEmailValid(invalidEmail);

        // then
        assertFalse(result);
    }
}
