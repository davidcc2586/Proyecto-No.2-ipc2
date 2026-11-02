package objetos.objetosDB;

import objetos.enums.Estado;
import objetos.objetosResponse.ConfiguracionColegio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConfiguracionColegioDB {
    public void nuevoAñoLectivo(int año) throws SQLException {
        try (Connection connection = ConnectionDB.getConexion()){
            try {
                connection.setAutoCommit(false);

                String sqlInactivar = "UPDATE ConfiguracionColegio SET estado = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInactivar)){
                    preparedStatement.setString(1,Estado.INACTIVO.name());
                    preparedStatement.executeUpdate();
                }

                String sqlNuevoAnioLectivo = "INSERT INTO ConfiguracionColegio(anioLectivo, estado) VALUE(?,?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlNuevoAnioLectivo)){
                    preparedStatement.setInt(1,año);
                    preparedStatement.setString(2,Estado.ACTIVO.name());
                    preparedStatement.executeUpdate();
                }

                connection.commit();
            } catch (Exception e) {
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    public int obtenerAñoLectivoActual() throws SQLException{
        String sqlSolicitarAño = "SELECT anioLectivo FROM ConfiguracionColegio WHERE estado = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarAño)){
            preparedStatement.setString(1, Estado.ACTIVO.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt("anioLectivo");
            }
        }
        return 0;
    }

    public void cambiaraAniLectivo(int anio) throws SQLException{
        try (Connection connection = ConnectionDB.getConexion()){

            try {
                connection.setAutoCommit(false);

                String sqlDesactivarAños = "UPDATE ConfiguracionColegio SET estado = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlDesactivarAños)){
                    preparedStatement.setString(1, Estado.INACTIVO.name());
                    preparedStatement.executeUpdate();
                }

                String sqlCambiarAño = "UPDATE  ConfiguracionColegio SET estado = ? WHERE anioLectivo = ? ";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCambiarAño)){
                    preparedStatement.setString(1, Estado.ACTIVO.name());
                    preparedStatement.setInt(2, anio);
                    preparedStatement.executeUpdate();
                }

                connection.commit();
            }catch (SQLException e){
                connection.rollback();
                throw new SQLException();
            }finally {
                connection.setAutoCommit(true);
            }

        }
    }

    public List<ConfiguracionColegio> obtenerAñosLectivos() throws SQLException{
        String sql = "select * from ConfiguracionColegio";
        List<ConfiguracionColegio> configuracionColegios = new ArrayList<>();
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                configuracionColegios.add(new ConfiguracionColegio(resultSet.getInt("anioLectivo"),Estado.valueOf(resultSet.getString("estado"))));
            }
            resultSet.close();
        }
        return configuracionColegios;
    }
}
