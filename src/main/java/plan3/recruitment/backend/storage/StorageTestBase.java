package plan3.recruitment.backend.storage;

import com.yammer.dropwizard.db.DatabaseConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import plan3.recruitment.backend.H2DatabaseManager;
import plan3.recruitment.backend.model.Person;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class StorageTestBase {

    private static final String USER = "sa";
    private static final String PASSWORD = "sa";

    private H2DatabaseManager databaseManager;

    protected SessionFactory getSessionFactory() {
        Configuration config = new Configuration()
                .addAnnotatedClass(Person.class)
                .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .setProperty("hibernate.connection.driver_class", "org.h2.Driver")
                .setProperty("hibernate.connection.url", "jdbc:h2:toos")
                .setProperty("hibernate.connection.username", USER)
                .setProperty("hibernate.connection.password", PASSWORD);
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();

        return config.buildSessionFactory(serviceRegistry);
    }

    protected void startDB() throws Exception {
        DatabaseConfiguration databaseConfiguration_mock = mock(DatabaseConfiguration.class);
        when(databaseConfiguration_mock.getUser()).thenReturn(USER);
        when(databaseConfiguration_mock.getPassword()).thenReturn(PASSWORD);

        databaseManager = new H2DatabaseManager(databaseConfiguration_mock);
        databaseManager.start();
    }

}
