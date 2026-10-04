package objetos.objetosDB;

import objetos.objetosClases.CurriculumCursosGrado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurriculumCursosGradoDB {

    public List<CurriculumCursosGrado> todosLosCurriculumCursosGrado() throws SQLException {
        Connection connection = DBConnectionSingleton.getInstance().getConnection();
        List<CurriculumCursosGrado> CurriculumsCursosGrado = new ArrayList<>();
        String sqlSolicitarCurriculumCursosGrado = "SELECT * FROM CurriculumCursosGrado";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarCurriculumCursosGrado);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()){
                CurriculumsCursosGrado.add(crearCurriculumCursosGrado(resultSet));
            }
        }

        return CurriculumsCursosGrado;
    }

    private CurriculumCursosGrado crearCurriculumCursosGrado(ResultSet resultSet) throws SQLException{
        int idGrado = resultSet.getInt("idGrado");
        int idCurso = resultSet.getInt("idCurso");
        return new CurriculumCursosGrado(idGrado,idCurso);
    }

}
