package clasesAuxiliares;

import objetos.enums.EstadoEstudiante;
import objetos.objetosDB.EstudianteDB;
import objetos.objetosResponse.Estudiante;
import objetos.objetosResponse.EstudianteSelectResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudianteServicio {
    public List<EstudianteSelectResponse> resumenEstudiantes(String estadoEstudiante) throws SQLException {

        EstudianteDB estudianteDB = new EstudianteDB();
        List<Estudiante> estudiantes = estudianteDB.obtenerEstudiantesSegunEstado(EstadoEstudiante.valueOf(estadoEstudiante));
        List<EstudianteSelectResponse> estudiateSelect = new ArrayList<>();
        for (Estudiante estudiante : estudiantes){
            estudiateSelect.add(new EstudianteSelectResponse(estudiante.getIdEstudiante(), estudiante.getNombre() + " " + estudiante.getApellido()));
        }
        return estudiateSelect;
    }
}
