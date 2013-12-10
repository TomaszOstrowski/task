package plan3.recruitment.backend;

import com.yammer.dropwizard.config.Configuration;
import plan3.recruitment.backend.resources.PersonResource;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class PersonDirectoryService extends Service<PersonDirectoryServiceConfiguration> {

    public static void main(final String[] args) throws Exception {
        new PersonDirectoryService().run(args);
    }

    @Override
    public void initialize(final Bootstrap<PersonDirectoryServiceConfiguration> bootstrap) {
        bootstrap.setName("PersonDirectoryService");
    }

    @Override
    public void run(PersonDirectoryServiceConfiguration configuration, Environment environment) throws Exception {
        environment.addResource(new PersonResource());
    }
}
