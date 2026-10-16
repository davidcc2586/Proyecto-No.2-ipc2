package objetos.objetosDB;

import objetos.objetosResponse.Seccion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeccionDB {

    public List<Seccion> todasLasSecciones() throws SQLException {
        List<Seccion> secciones = new ArrayList<>();
        String sqlSolicitarSecciones = "SELECT * FROM Seccion";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarSecciones);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()){
                secciones.add(crearSeccion(resultSet));
            }
        }
        return secciones;
    }

    public void InsertarNuevaSeccion(Seccion seccion) throws SQLException{
        String sqlNuevaSeccion = "INSERT INTO Seccion(idGrado, anio, seccion) VALUES (?,?,?)";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlNuevaSeccion)){
            preparedStatement.setInt(1, seccion.getIdGrado());
            preparedStatement.setInt(2, seccion.getAnio());
            preparedStatement.setInt(1, seccion.getSeccion());
            preparedStatement.executeUpdate();
        }
    }

    public Seccion crearSeccion(ResultSet resultSet) throws SQLException{
        int idSeccion = resultSet.getInt("idSeccion");
        int idGrado = resultSet.getInt("idGrado");
        int anio = resultSet.getInt("anio");
        char seccion = resultSet.getString("seccion").charAt(0);
        return new Seccion(idSeccion,idGrado,anio,seccion);
    }
}
