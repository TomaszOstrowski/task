package plan3.recruitment.backend.health;

import com.yammer.metrics.core.HealthCheck;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import plan3.recruitment.backend.resources.PersonResource;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonResourceHealthCheckTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private PersonResource personResource_mock;

    private PersonResourceHealthCheck healthCheck;

    @Before
    public void setUp() {
        healthCheck = new PersonResourceHealthCheck(personResource_mock);
    }

    @Test
    public void shouldNotInstantiateClassIfNullPassedDuringConstruction() {
        // given
        exception.expect(NullPointerException.class);

        // when
        new PersonResourceHealthCheck(null);

        // then Exception is thrown
    }

    @Test
    public void shouldReportUnhealthyIfInvalidEmailWasProcessed() throws Exception {
        // given invalid email is processed

        // when
        HealthCheck.Result result = healthCheck.check();

        // then
        assertFalse(result.isHealthy());
    }

    @Test
    public void shouldReportHealthyIfProperExceptionForInvalidEmail() throws Exception {
        // given
        when(personResource_mock.fetch(anyString())).thenThrow(new WebApplicationException(Response.Status.BAD_REQUEST));

        // when
        HealthCheck.Result result = healthCheck.check();

        // then
        assertTrue(result.isHealthy());
    }
}
