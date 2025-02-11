package ma.rougga.qdata;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class CPConnection {

    private static HikariDataSource dataSource;
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String CONNECTIONSTRING = "jdbc:postgresql://localhost:5432/postgres?characterEncoding=UTF-8";
    private static final String USER = "honyi";
    private static final String PASSWORD = "honyi123";

    static {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(DRIVER);
        config.setJdbcUrl(CONNECTIONSTRING);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(10); // Max connections
        config.setMinimumIdle(2); // Min idle connections
        config.setIdleTimeout(30000); // Close idle connections after 30 sec
        config.setConnectionTimeout(3000); // Wait max 3 sec for a connection
        config.setLeakDetectionThreshold(5000); // Detects connection leaks
        config.setMaxLifetime(1800000); //30 min
        config.setAutoCommit(true);
        dataSource = new HikariDataSource(config);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public Statement getStatement() throws SQLException {
        return getDataSource().getConnection().createStatement();
    }

    public Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

}
