package plan3.recruitment.backend;

import plan3.recruitment.backend.resources.PersonResource;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class PersonDirectoryService extends Service {

    @Override
    public void initialize(final Bootstrap bootstrap) {
    }

    public static void main(final String[] args) throws Exception {
        new PersonDirectoryService().run(args);
    }
}
