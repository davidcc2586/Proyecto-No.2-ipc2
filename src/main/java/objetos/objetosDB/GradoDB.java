package objetos.objetosDB;

import objetos.enums.GradoColegio;
import objetos.objetosClases.Grado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradoDB {

    public List<Grado> todosLosGrados() throws SQLException {
        Connection connection = DBConnectionSingleton.getInstance().getConnection();
        List<Grado> grados = new ArrayList<>();
        String sqlSolicitarGrados = "SELECT * FROM Grado";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarGrados);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()){
                grados.add(crearGrado(resultSet));
            }
        }

        return grados;
    }

    private Grado crearGrado(ResultSet resultSet) throws SQLException{
        int idGrado = resultSet.getInt("idGrado");
        GradoColegio gradoColegio = GradoColegio.valueOf(resultSet.getString("grado"));
        int idCarrera = resultSet.getInt("idCarrera");
        return new Grado(idGrado, gradoColegio, idCarrera);
    }
}
