package plan3.recruitment.backend.health;

import com.yammer.metrics.core.HealthCheck;
import plan3.recruitment.backend.resources.PersonResource;

import javax.ws.rs.WebApplicationException;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

public class PersonResourceHealthCheck extends HealthCheck {

    private final PersonResource personResource;

    public PersonResourceHealthCheck(PersonResource personResource) {
        super("PersonResource email validation");
        this.personResource = checkNotNull(personResource, "personResource must not be null.");
    }

    @Override

    protected Result check() throws Exception {
        String invalidEmail = "health-check-please-ignore-me@";

        int status = getStatusForInvalidEmailFetch(invalidEmail);

        if (status == BAD_REQUEST.getStatusCode()) {
            return Result.healthy();
        }

        return Result.unhealthy("Invalid email was accepted. Validation does not work.");
    }

    private int getStatusForInvalidEmailFetch(String invalidEmail) {
        int status = -1;
        try {
            personResource.fetch(invalidEmail);
        } catch (WebApplicationException webAppEx) {
            status = webAppEx.getResponse().getStatus();
        }
        return status;
    }
}
