package clasesAuxiliares;

import objetos.enums.EstadoEstudiante;
import objetos.exceptions.ValidacionException;
import objetos.objetosRequest.EstudianteActualizarRequest;
import objetos.objetosDB.EstudianteDB;
import objetos.objetosResponse.Estudiante;
import objetos.objetosResponse.EstudianteDatosResponse;
import objetos.objetosResponse.EstudianteSelectResponse;
import objetos.objetosResponse.PaginaResponse;

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

    public List<Estudiante> estudiantesSeccion(int idSeccion) throws SQLException{
        EstudianteDB estudianteDB = new EstudianteDB();
        return estudianteDB.estudiantesSeccion(idSeccion);
    }

    public PaginaResponse<Estudiante> estudiantesSeccionPaginados(int idSeccion, int pagina, int tamano) throws SQLException {
        EstudianteDB estudianteDB = new EstudianteDB();
        return estudianteDB.estudiantesSeccionPaginados(idSeccion, pagina, tamano);
    }

    public PaginaResponse<Estudiante> estudiantesPorEstadoPaginados(String estadoEstudiante, int pagina, int tamano) throws SQLException {
        EstudianteDB estudianteDB = new EstudianteDB();
        return estudianteDB.estudiantesPorEstadoPaginados(EstadoEstudiante.valueOf(estadoEstudiante), pagina, tamano);
    }

    public EstudianteDatosResponse datosEstudiante(int idEstudiante) throws SQLException{
        EstudianteDB estudianteDB = new EstudianteDB();
        return estudianteDB.datosEstudiante(idEstudiante);
    }

    public void actualizarEstudiante(EstudianteActualizarRequest estudiante) throws SQLException, ValidacionException {
        EstudianteDB estudianteDB = new EstudianteDB();
        estudianteDB.actualizarEstudiante(estudiante);
    }


    public void cambiaEstadoEstudiante(int idEstudiante, EstadoEstudiante estadoEstudiante) throws SQLException{
        EstudianteDB estudianteDB = new EstudianteDB();
        estudianteDB.cambiarEstadoEstudiante(idEstudiante,estadoEstudiante);
    }
}
