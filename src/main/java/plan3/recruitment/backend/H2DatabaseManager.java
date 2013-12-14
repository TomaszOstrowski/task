package plan3.recruitment.backend;

import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.lifecycle.Managed;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.SQLException;

import static com.google.common.base.Preconditions.checkNotNull;

public class H2DatabaseManager implements Managed {

    private static final String URL_WITH_INIT_SCRIPT =
            "jdbc:h2:toos;INIT=RUNSCRIPT FROM 'src/main/resources/create.sql'\\;";

    private final DatabaseConfiguration databaseConfiguration;
    private Server server;

    public H2DatabaseManager(DatabaseConfiguration databaseConfiguration) {
        this.databaseConfiguration = checkNotNull(databaseConfiguration);
    }

    @Override
    public void start() throws Exception {
        server = Server.createTcpServer();
        server.start();
        populateDB();
    }

    private void populateDB() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();

        dataSource.setURL(URL_WITH_INIT_SCRIPT);
        dataSource.setUser(databaseConfiguration.getUser());
        dataSource.setPassword(databaseConfiguration.getPassword());

        Connection connection = dataSource.getConnection();
        connection.close();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }
}
