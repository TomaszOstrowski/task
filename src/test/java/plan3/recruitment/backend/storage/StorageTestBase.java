package plan3.recruitment.backend.storage;

import com.yammer.dropwizard.db.DatabaseConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import plan3.recruitment.backend.H2DatabaseManager;
import plan3.recruitment.backend.model.Person;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class StorageTestBase {

    private static final String USER = "sa";
    private static final String PASSWORD = "sa";
    private static final String URL = "jdbc:h2:toos";
    private static final String H2_DRIVER = "org.h2.Driver";
    private static final String H2_DIALECT = "org.hibernate.dialect.H2Dialect";
    private static final String THREAD = "thread";

    private static H2DatabaseManager databaseManager;
    protected static SessionFactory sessionFactory;

    @BeforeClass
    public static void startDB() throws Exception {
        DatabaseConfiguration databaseConfiguration_mock = mock(DatabaseConfiguration.class);
        when(databaseConfiguration_mock.getUser()).thenReturn(USER);
        when(databaseConfiguration_mock.getPassword()).thenReturn(PASSWORD);

        databaseManager = new H2DatabaseManager(databaseConfiguration_mock);
        databaseManager.start();

        sessionFactory = getSessionFactory();
    }

    @Before
    public void beginTransaction() throws Exception {
        sessionFactory.getCurrentSession().beginTransaction();
    }

    @After
    public void rollbackTransaction() throws Exception {
        sessionFactory.getCurrentSession().getTransaction().rollback();
    }

    @AfterClass
    public static void stopDB() throws Exception {
        databaseManager.stop();
    }

    private static SessionFactory getSessionFactory() {
        Configuration config = new Configuration()
                .addAnnotatedClass(Person.class)
                .setProperty("hibernate.dialect", H2_DIALECT)
                .setProperty("hibernate.connection.driver_class", H2_DRIVER)
                .setProperty("hibernate.connection.url", URL)
                .setProperty("hibernate.connection.username", USER)
                .setProperty("hibernate.connection.password", PASSWORD)
                .setProperty("hibernate.current_session_context_class", THREAD);

        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                .applySettings(config.getProperties()).buildServiceRegistry();

        return config.buildSessionFactory(serviceRegistry);
    }
}
