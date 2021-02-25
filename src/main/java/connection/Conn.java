package connection;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig; 
import com.zaxxer.hikari.HikariDataSource;

public class Conn {

	private static DataSource pool = null;

	public static Connection getConnection() throws SQLException {
		if (pool != null) {
			return pool.getConnection();
		}
		// The configuration object specifies behaviors for the connection pool.
		HikariConfig config = new HikariConfig();
		// Configure which instance and what database user to connect with.
		config.setDriverClassName(System.getProperty("drivername")); // see appengine-web.xml
		config.setJdbcUrl("jdbc:mysql://localhost:3306/" + System.getProperty("databasename")); // see appengine-web.xml
		config.setUsername(System.getProperty("localusername")); // see appengine-web.xml
		config.setPassword(System.getProperty("localpassword")); // see appengine-web.xml

		// Initialize the connection pool using the configuration object.
		pool = new HikariDataSource(config);

		return pool.getConnection();
	}
}