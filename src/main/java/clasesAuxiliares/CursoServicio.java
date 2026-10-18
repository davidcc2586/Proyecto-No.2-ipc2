package clasesAuxiliares;

import objetos.objetosDB.CursoDB;
import objetos.objetosResponse.Curso;

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
