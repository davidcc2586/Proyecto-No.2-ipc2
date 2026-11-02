package clasesAuxiliares;

import objetos.exceptions.ValidacionException;
import objetos.objetosDB.AsignacionEstudiantesDB;
import objetos.objetosRequest.AsignacionEstudianteRequest;

import java.sql.SQLException;

public class AsignacionEstudiantesServicio {

    public void asignarEstudiante(AsignacionEstudianteRequest asignacion) throws SQLException,ValidacionException {
        AsignacionEstudiantesDB asignacionEstudiantesDB = new AsignacionEstudiantesDB();
        if (asignacionEstudiantesDB.isEstudianteAsignado(asignacion)){
            throw new ValidacionException("El estudiante se encuentra asignado a una sección");
        }
        asignacionEstudiantesDB.asignarEstudiante(asignacion);
    }
}
