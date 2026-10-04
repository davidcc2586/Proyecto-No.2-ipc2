package objetos.objetosDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionSingleton {
    private static final String IP = "localhost";
    private static final int PUERTO = 3306;
    private static final String SCHEMA = "eventos";
    private static final String USER_NAME = "josue";
    private static final String PASSWORD = "josue100000";
    private static final String URL = "jdbc:mysql://" + IP + ":" + PUERTO + "/" + SCHEMA;

    private static DBConnectionSingleton instance;

    private Connection connection;

    private DBConnectionSingleton() throws SQLException {
        connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    }

    public static DBConnectionSingleton getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnectionSingleton();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        }
        return connection;
    }
}
