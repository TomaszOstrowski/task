package plan3.recruitment.backend;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.hibernate.HibernateBundle;
import com.yammer.dropwizard.migrations.MigrationsBundle;
import liquibase.database.core.H2Database;
import plan3.recruitment.backend.model.Person;
import plan3.recruitment.backend.model.PersonStorage;
import plan3.recruitment.backend.resources.InMemoryPersonStorage;
import plan3.recruitment.backend.resources.PersonResource;

public class PersonDirectoryService extends Service<PersonDirServiceConf> {

    private final HibernateBundle<PersonDirServiceConf> hibernate = new HibernateBundle<PersonDirServiceConf>(Person.class) {
        @Override
        public DatabaseConfiguration getDatabaseConfiguration(PersonDirServiceConf configuration) {
            return configuration.getDatabaseConfiguration();
        }
    };

    public static void main(final String[] args) throws Exception {
        new PersonDirectoryService().run(args);
    }

    @Override
    public void initialize(final Bootstrap<PersonDirServiceConf> bootstrap) {
        bootstrap.setName("PersonDirectoryService");
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(PersonDirServiceConf configuration, Environment environment) throws Exception {
        environment.manage(new H2DatabaseManager(configuration.getDatabaseConfiguration()));

        final PersonStorage personStorage = new InMemoryPersonStorage(hibernate.getSessionFactory());
        environment.addResource(new PersonResource(personStorage));
    }
}
