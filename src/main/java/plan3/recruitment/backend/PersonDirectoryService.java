package plan3.recruitment.backend;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.hibernate.HibernateBundle;
import com.yammer.dropwizard.migrations.MigrationsBundle;
import plan3.recruitment.backend.model.Person;
import plan3.recruitment.backend.model.PersonStorage;
import plan3.recruitment.backend.resources.InMemoryPersonStorage;
import plan3.recruitment.backend.resources.PersonResource;

public class PersonDirectoryService extends Service<PersonDirectoryServiceConfiguration> {

    private final HibernateBundle<PersonDirectoryServiceConfiguration> hibernate
            = new HibernateBundle<PersonDirectoryServiceConfiguration>(Person.class) {
        @Override
        public DatabaseConfiguration getDatabaseConfiguration(PersonDirectoryServiceConfiguration configuration) {
            return configuration.getDatabaseConfiguration();
        }
    };

    public static void main(final String[] args) throws Exception {
        new PersonDirectoryService().run(args);
    }

    @Override
    public void initialize(final Bootstrap<PersonDirectoryServiceConfiguration> bootstrap) {
        bootstrap.setName("PersonDirectoryService");
        bootstrap.addBundle(new MigrationsBundle<PersonDirectoryServiceConfiguration>() {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(PersonDirectoryServiceConfiguration personDirectoryServiceConfiguration) {
                return personDirectoryServiceConfiguration.getDatabaseConfiguration();
            }
        });
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(PersonDirectoryServiceConfiguration configuration, Environment environment) throws Exception {
        final PersonStorage personStorage = new InMemoryPersonStorage(hibernate.getSessionFactory());
        environment.addResource(new PersonResource(personStorage));
    }
}
