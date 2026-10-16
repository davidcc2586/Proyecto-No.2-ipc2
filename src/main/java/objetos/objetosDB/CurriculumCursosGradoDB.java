package objetos.objetosDB;

import objetos.objetosResponse.CurriculumCursosGrado;
import objetos.objetosResponse.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurriculumCursosGradoDB {

    public List<Curso> cursosCurriculumGrado(int idGrado) throws SQLException{
        List<CurriculumCursosGrado> curriculumCursosGradoRespons = curriculumGrado(idGrado);
        CursoDB cursoDB = new CursoDB();
        List<Curso> cursoResponses = new ArrayList<>();
        for (CurriculumCursosGrado curriculumCursosGrado : curriculumCursosGradoRespons){
            Optional<Curso> curso = cursoDB.obtenerCurso(curriculumCursosGrado.getIdCurso());
            if (curso.isPresent()){
                cursoResponses.add(curso.get());
            }
        }
        return cursoResponses;
    }

    private List<CurriculumCursosGrado> curriculumGrado(int idGrado) throws SQLException {
        List<CurriculumCursosGrado> CurriculumsCursosGrado = new ArrayList<>();
        String sqlSolicitarCurriculumCursosGrado = "SELECT * FROM CurriculumCursosGrado WHERE idGrado = ?";
        try(Connection connection = ConnectionDB.getConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarCurriculumCursosGrado)){
            preparedStatement.setInt(1,idGrado);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                CurriculumsCursosGrado.add(crearCurriculumCursosGrado(resultSet));
            }
        }

        return CurriculumsCursosGrado;
    }
    public void ingresarNuevoCursoCurriculum(CurriculumCursosGrado curriculumCursosGrado) throws SQLException{
        String sqlIngresar = "insert into CurriculumCursosGrado(idGrado, idCurso) VALUES(?,?)";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresar)){
            preparedStatement.setInt(1, curriculumCursosGrado.getIdGrado());
            preparedStatement.setInt(1, curriculumCursosGrado.getIdCurso());
            preparedStatement.executeUpdate();
        }
    }
    private CurriculumCursosGrado crearCurriculumCursosGrado(ResultSet resultSet) throws SQLException{
        int idGrado = resultSet.getInt("idGrado");
        int idCurso = resultSet.getInt("idCurso");
        return new CurriculumCursosGrado(idGrado,idCurso);
    }

}
