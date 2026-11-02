package clasesAuxiliares;

import objetos.exceptions.ValidacionException;
import objetos.objetosDB.CurriculumCursosGradoDB;
import objetos.objetosRequest.CurriculumCursosGradoRequest;
import objetos.objetosResponse.Curso;
import objetos.objetosResponse.PaginaResponse;

import java.sql.SQLException;
import java.util.List;

public class CurriculumCursosGradoServicio {

    public List<Curso> catalogo(int idGrado) throws SQLException {
        CurriculumCursosGradoDB curriculumCursosGradoDB = new CurriculumCursosGradoDB();
        return curriculumCursosGradoDB.cursosCurriculumGrado(idGrado);
    }

    public PaginaResponse<Curso> catalogoPaginado(int idGrado, int pagina, int tamano) throws SQLException {
        CurriculumCursosGradoDB curriculumCursosGradoDB = new CurriculumCursosGradoDB();
        return curriculumCursosGradoDB.cursosCurriculumGradoPaginados(idGrado, pagina, tamano);
    }

    public void asignarCursoGrado(CurriculumCursosGradoRequest curriculumCursosGradoRequest) throws SQLException,ValidacionException{
        CurriculumCursosGradoDB curriculumCursosGradoDB = new CurriculumCursosGradoDB();
        if (curriculumCursosGradoDB.existeAsignacion(curriculumCursosGradoRequest)){
            throw new ValidacionException("La asignación de este curso al grado ya existe.");
        }
        curriculumCursosGradoDB.ingresarNuevoCursoCurriculum(curriculumCursosGradoRequest);
    }
}
