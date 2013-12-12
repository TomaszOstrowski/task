package plan3.recruitment.backend.health;

import com.yammer.metrics.core.HealthCheck;
import plan3.recruitment.backend.model.Person;
import plan3.recruitment.backend.resources.PersonResource;

public class PersonResourceHealthCheck extends HealthCheck {

    private final PersonResource personResource;

    public PersonResourceHealthCheck(PersonResource personResource) {
        super("PersonResource save&fetch");
        this.personResource = personResource;
    }

    @Override
    protected Result check() throws Exception {
        String healthCheckEmail = "please@ignore.me";
        Person healthCheck = Person.valueOf("Health" , "Check", healthCheckEmail);
        personResource.save(healthCheck);

        Result result;
        try {
            personResource.fetch(healthCheckEmail);
            result = Result.healthy();
        } catch (Exception anyException) {
            result = Result.unhealthy("Exception message: " + anyException.getMessage());
        }

        return result;
    }
}
