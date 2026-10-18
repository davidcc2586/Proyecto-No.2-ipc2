package clasesAuxiliares;

import objetos.objetosDB.AsignacionEstudiantesDB;
import objetos.objetosRequest.AsignacionEstudianteRequest;

import java.sql.SQLException;

public class AsignacionEstudiantesServicio {

    public void asignarEstudiante(AsignacionEstudianteRequest asignacion) throws SQLException {
        AsignacionEstudiantesDB asignacionEstudiantesDB = new AsignacionEstudiantesDB();
        asignacionEstudiantesDB.asignarEstudiante(asignacion);
    }
}
