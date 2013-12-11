package plan3.recruitment.backend;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.hibernate.HibernateBundle;
import plan3.recruitment.backend.model.Person;
import plan3.recruitment.backend.storage.PersonStorage;
import plan3.recruitment.backend.storage.InMemoryPersonStorage;
import plan3.recruitment.backend.resources.PersonResource;

public class PersonDirectoryService extends Service<PersonDirectoryServiceConf> {

    private final HibernateBundle<PersonDirectoryServiceConf> hibernate =
            new HibernateBundle<PersonDirectoryServiceConf>(Person.class) {
        @Override
        public DatabaseConfiguration getDatabaseConfiguration(PersonDirectoryServiceConf configuration) {
            return configuration.getDatabaseConfiguration();
        }
    };

    public static void main(final String[] args) throws Exception {
        new PersonDirectoryService().run(args);
    }

    @Override
    public void initialize(final Bootstrap<PersonDirectoryServiceConf> bootstrap) {
        bootstrap.setName("PersonDirectoryService");
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(PersonDirectoryServiceConf configuration, Environment environment) throws Exception {
        environment.manage(new H2DatabaseManager(configuration.getDatabaseConfiguration()));

        final PersonStorage personStorage = new InMemoryPersonStorage(hibernate.getSessionFactory());
        environment.addResource(new PersonResource(personStorage));
    }
}
