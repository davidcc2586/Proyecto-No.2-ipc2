package objetos.objetosDB;

import objetos.objetosClases.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroDB {

    public List<Libro> todosLosLibros() throws SQLException {

        Connection connection = DBConnectionSingleton.getInstance().getConnection();
        List<Libro> libros = new ArrayList<>();
        String sqlSolicitarLibros = "SELECT * FROM Libro";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarLibros);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()){
                libros.add(crearLibro(resultSet));
            }
        }

        return libros;
    }

    private Libro crearLibro(ResultSet resultSet) throws SQLException{
        int idLibro = resultSet.getInt("idLibro");
        int autor = resultSet.getInt("autor");
        int anio = resultSet.getInt("anio");
        String titulo = resultSet.getString("titulo");
        String editorial = resultSet.getString("editorial");
        int cantidad = resultSet.getInt("cantidad");
        return new Libro(idLibro,autor,anio,titulo,editorial,cantidad);
    }
}
