package plan3.recruitment.backend.validators;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnitParamsRunner.class)
public class InputValidatorTest {

    private final InputValidator inputValidator = new InputValidator();

    @Test
    public void shouldReturnTrueForValidEmail() {
        // given
        String validEmail = "qiuck@brown.fox";

        // when
        boolean result = inputValidator.isEmailValid(validEmail);

        // then
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseForNull() {
        // given
        String nullEmail = null;

        // when
        boolean result = inputValidator.isEmailValid(nullEmail);

        // then
        assertFalse(result);
    }

    @Test
    @Parameters({"whatever", "whatever@", "whatever@@gmail.com", "@whatever" , "", "  "})
    public void shouldReturnFalseForInvalidEmail(String invalidEmail) {
        // given each invalid email from @Parameters list

        // when
        boolean result = inputValidator.isEmailValid(invalidEmail);

        // then
        assertFalse(result);
    }
}
