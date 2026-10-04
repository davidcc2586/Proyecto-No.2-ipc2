package objetos.objetosDB;

import objetos.enums.TipoActividad;
import objetos.objetosClases.Actividad;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActividadDB {

    private Connection connection;

    private Actividad crearActividad(ResultSet resultSet) throws SQLException{
        int idActividad = resultSet.getInt("idActividad");
        int idSeccion = resultSet.getInt("idSeccion");
        int idCurso = resultSet.getInt("idCurso");
        String nombreActividad = resultSet.getString("nombreActividad");
        double ponderacion = resultSet.getDouble("ponderacion");
        TipoActividad tipoActividad = TipoActividad.valueOf(resultSet.getString("tipo"));
        return new Actividad(idActividad,idSeccion,idCurso,nombreActividad,ponderacion,tipoActividad);
    }
}
