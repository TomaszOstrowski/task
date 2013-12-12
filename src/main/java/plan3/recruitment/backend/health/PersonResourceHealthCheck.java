package plan3.recruitment.backend.health;

import com.yammer.metrics.core.HealthCheck;
import plan3.recruitment.backend.resources.PersonResource;

public class PersonResourceHealthCheck extends HealthCheck {

    private final PersonResource personResource;

    public PersonResourceHealthCheck(PersonResource personResource) {
        super("PersonResource");
        this.personResource = personResource;
    }

    @Override

    protected Result check() throws Exception {
        return Result.healthy();
    }
}
