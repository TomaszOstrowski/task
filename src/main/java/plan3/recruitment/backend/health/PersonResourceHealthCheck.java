package plan3.recruitment.backend.health;

import com.yammer.metrics.core.HealthCheck;
import plan3.recruitment.backend.resources.PersonResource;

import javax.ws.rs.WebApplicationException;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

public class PersonResourceHealthCheck extends HealthCheck {

    private final PersonResource personResource;

    public PersonResourceHealthCheck(PersonResource personResource) {
        super("PersonResource email validation");
        this.personResource = personResource;
    }

    @Override

    protected Result check() throws Exception {
        String invalidEmail = "health-check-please-ignore-me@";

        int status = -1;

        try {
            personResource.fetch(invalidEmail);
        } catch (WebApplicationException webAppEx) {
            status = webAppEx.getResponse().getStatus();
        }

        if (BAD_REQUEST.getStatusCode() == status) {
            return Result.healthy();
        }

        return Result.unhealthy("Invalid email was passed to DB. Validation does not work.");
    }
}
