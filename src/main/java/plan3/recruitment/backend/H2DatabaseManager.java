package plan3.recruitment.backend;

import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.lifecycle.Managed;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.SQLException;

public class H2DatabaseManager implements Managed {

    private final DatabaseConfiguration databaseConfiguration;
    private Server server;

    public H2DatabaseManager(DatabaseConfiguration databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
    }

    @Override
    public void start() throws Exception {
        server = Server.createTcpServer();
        server.start();
        populateDB();
    }

    private void populateDB() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();

        dataSource.setURL(getUrl());
        dataSource.setUser(databaseConfiguration.getUser());
        dataSource.setPassword(databaseConfiguration.getPassword());

        Connection connection = dataSource.getConnection();
        connection.close();
    }

    private String getUrl() {
        return "jdbc:h2:toos;INIT=RUNSCRIPT FROM 'src/main/resources/create.sql'\\;";
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }
}
