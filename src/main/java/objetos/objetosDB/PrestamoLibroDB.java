package objetos.objetosDB;

import objetos.enums.EstadoFinalPrestamoLibro;
import objetos.enums.EstadoPrestamoLibro;
import objetos.objetosClases.PrestamoLibro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoLibroDB {

    public List<PrestamoLibro> todosLosPrestamosLibro() throws SQLException {
        Connection connection = DBConnectionSingleton.getInstance().getConnection();
        List<PrestamoLibro> prestamosLIbro = new ArrayList<>();
        String sqlSolicitarPrestamosLibro = "SELECT * FROM PrestamoLibro";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarPrestamosLibro);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()){
                prestamosLIbro.add(crearPrestamoLibro(resultSet));
            }
        }

        return prestamosLIbro;
    }

    private PrestamoLibro crearPrestamoLibro(ResultSet resultSet) throws SQLException{
        int idPrestamoLibro = resultSet.getInt("idPrestamoLibro");
        int idLibro = resultSet.getInt("idLibro");
        int idEstudiante = resultSet.getInt("idEstudiante");
        EstadoPrestamoLibro estadoPrestamoLibro = EstadoPrestamoLibro.valueOf(resultSet.getString("estadoPrestamo"));
        EstadoFinalPrestamoLibro estadoFinalPrestamoLibro = EstadoFinalPrestamoLibro.valueOf(resultSet.getString("estadoFinal"));
        double costoDia = resultSet.getDouble("costoDia");
        LocalDate fechaPrestamo = resultSet.getDate("fechaPrestamo").toLocalDate();
        LocalDate fechaEstimadaDevolucion = resultSet.getDate("fechaEstimadaDevolucion").toLocalDate();
        double multa = resultSet.getDouble("multa");
        return new PrestamoLibro(idPrestamoLibro,idLibro,idEstudiante,estadoPrestamoLibro,estadoFinalPrestamoLibro,costoDia,fechaPrestamo,fechaEstimadaDevolucion,multa);
    }
}
