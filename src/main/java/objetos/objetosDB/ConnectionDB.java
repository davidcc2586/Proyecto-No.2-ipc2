package objetos.objetosDB;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionDB {

    private static final String URL = "java:comp/env/jdbc/ColegioPrestigioso";
    private static DataSource dataSource;

    static {
        try {
            dataSource = (DataSource) new InitialContext().lookup(URL);
        } catch (NamingException e) {
            throw new IllegalStateException("No se pudo resolver el DataSource JNDI: " + URL, e);
        }
    }

    public static Connection getConexion() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("El DataSource no fue inicializado correctamente.");
        }
        return dataSource.getConnection();
    }
}