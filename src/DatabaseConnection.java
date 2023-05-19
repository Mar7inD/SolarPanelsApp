import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection implements AutoCloseable
{
    private Connection connection;

    public DatabaseConnection() throws SQLException {
      String url = "jdbc:postgresql://snuffleupagus.db.elephantsql.com:5432/icgiivdi";
      String username = "icgiivdi";
      String password = "5JBbbzO0mgAwhwoUtAkf3QH8zaL4Lo-n";
      connection = DriverManager.getConnection(url, username, password);
    }

    @Override
    public void close() throws SQLException {
      if (connection != null) {
        connection.close();
      }
    }
}
