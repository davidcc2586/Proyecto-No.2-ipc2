package objetos.objetosDB;

import objetos.objetosClases.Seccion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeccionSB {

    public List<Seccion> todasLasSecciones() throws SQLException {
        Connection connection = DBConnectionSingleton.getInstance().getConnection();
        List<Seccion> secciones = new ArrayList<>();
        String sqlSolicitarSecciones = "SELECT * FROM Seccion";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarSecciones);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()){
                secciones.add(crearSeccion(resultSet));
            }
        }

        return secciones;
    }

    private Seccion crearSeccion(ResultSet resultSet) throws SQLException{
        int idSeccion = resultSet.getInt("idSeccion");
        int idGrado = resultSet.getInt("idGrado");
        int anio = resultSet.getInt("anio");
        char seccion = resultSet.getString("seccion").charAt(0);
        return new Seccion(idSeccion,idGrado,anio,seccion);
    }
}
