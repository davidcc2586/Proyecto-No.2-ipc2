package clasesAuxiliares;

import objetos.objetosDB.CursoDB;
import objetos.objetosResponse.Curso;
import objetos.objetosResponse.PaginaResponse;

import java.sql.SQLException;
import java.util.List;

public class CursoServicio {
    public List<Curso> cursosMaestro(int idMaestro) throws SQLException{
        CursoDB cursoDB = new CursoDB();
        return cursoDB.cursosDeUsuarios(idMaestro,false);
    }

    public List<Curso> cursosEstudiante (int idEstudiente) throws SQLException{
        CursoDB cursoDB = new CursoDB();
        return cursoDB.cursosDeUsuarios(idEstudiente,true);
    }

    public void ingresarNuevoCurso(String nombreCurso) throws SQLException{
        CursoDB cursoDB = new CursoDB();
        cursoDB.ingresarNuevoCurso(nombreCurso);
    }
    public List<Curso> cursosDisponibles(String estado) throws SQLException{
        CursoDB cursoDB = new CursoDB();
        return cursoDB.cursosDisponibles(estado);
    }

    public PaginaResponse<Curso> cursosDisponiblesPaginados(String estado, int pagina, int tamano) throws SQLException {
        CursoDB cursoDB = new CursoDB();
        return cursoDB.cursosDisponiblesPaginados(estado, pagina, tamano);
    }

    public PaginaResponse<Curso> cursosMaestroPaginados(int idMaestro, int pagina, int tamano) throws SQLException {
        CursoDB cursoDB = new CursoDB();
        return cursoDB.cursosDeUsuariosPaginados(idMaestro, false, pagina, tamano);
    }

    public PaginaResponse<Curso> cursosEstudiantePaginados(int idEstudiante, int pagina, int tamano) throws SQLException {
        CursoDB cursoDB = new CursoDB();
        return cursoDB.cursosDeUsuariosPaginados(idEstudiante, true, pagina, tamano);
    }

    public void activarDesactivarCurso(int idCurso) throws SQLException{
        CursoDB cursoDB = new CursoDB();
        cursoDB.activarDesactivarCurso(idCurso);
    }

    public void actualizarCurso(int idCurso, String nombreActualizado) throws SQLException{
        CursoDB cursoDB = new CursoDB();
        cursoDB.actualizarCurso(idCurso, nombreActualizado);
    }

    public Curso obtenerCurso(int idCurso) throws SQLException{
        CursoDB cursoDB = new CursoDB();
        return cursoDB.obtenerCurso(idCurso).get();
    }
}
