package clasesAuxiliares;

import objetos.objetosDB.CurriculumCursosGradoDB;
import objetos.objetosRequest.CurriculumCursosGradoRequest;
import objetos.objetosResponse.Curso;

import java.sql.SQLException;
import java.util.List;

public class CurriculumCursosGradoServicio {

    public List<Curso> catalogo(int idGrado) throws SQLException {
        CurriculumCursosGradoDB curriculumCursosGradoDB = new CurriculumCursosGradoDB();
        return curriculumCursosGradoDB.cursosCurriculumGrado(idGrado);
    }

    public void asignarCursoGrado(CurriculumCursosGradoRequest curriculumCursosGradoRequest) throws SQLException{
        CurriculumCursosGradoDB curriculumCursosGradoDB = new CurriculumCursosGradoDB();
        curriculumCursosGradoDB.ingresarNuevoCursoCurriculum(curriculumCursosGradoRequest);
    }
}
