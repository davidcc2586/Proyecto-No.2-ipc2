package objetos.objetosDB;

import objetos.objetosRequest.SeccionActualizarRequest;
import objetos.objetosRequest.SeccionRequest;
import objetos.objetosResponse.Seccion;
import objetos.objetosResponse.PaginaResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeccionDB {

    public List<Seccion> seccionesGrado(int idGrado) throws SQLException {
        List<Seccion> secciones = new ArrayList<>();
        String sqlSolicitarSecciones = "SELECT * FROM Seccion WHERE idGrado = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarSecciones)) {
            preparedStatement.setInt(1,idGrado);
             ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                secciones.add(crearSeccion(resultSet));
            }
        }
        return secciones;
    }

    public PaginaResponse<Seccion> seccionesGradoPaginadas(int idGrado, int pagina, int tamano) throws SQLException {
        int offset = new objetos.Paginacion().calcularDesplazamiento(pagina, tamano);
        List<Seccion> secciones = new ArrayList<>();
        int totalElementos = 0;
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement conteo = connection.prepareStatement("SELECT COUNT(*) FROM Seccion WHERE idGrado = ?")) {
            conteo.setInt(1, idGrado);
            try (ResultSet resultado = conteo.executeQuery()) {
                if (resultado.next()) {
                    totalElementos = resultado.getInt(1);
                }
            }
            try (PreparedStatement consulta = connection.prepareStatement("SELECT * FROM Seccion WHERE idGrado = ? ORDER BY idSeccion LIMIT ? OFFSET ?")) {
                consulta.setInt(1, idGrado);
                consulta.setInt(2, tamano);
                consulta.setInt(3, offset);
                try (ResultSet resultado = consulta.executeQuery()) {
                    while (resultado.next()) {
                        secciones.add(crearSeccion(resultado));
                    }
                }
            }
        }
        return new PaginaResponse<>(secciones, pagina, tamano, totalElementos);
    }

    public void insertarNuevaSeccion(SeccionRequest seccion) throws SQLException{
        String sqlNuevaSeccion = "INSERT INTO Seccion(idGrado, anio, seccion) VALUES (?,?,?)";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlNuevaSeccion)){
            preparedStatement.setInt(1, Integer.parseInt(seccion.getIdGrado()));
            preparedStatement.setInt(2, Integer.parseInt(seccion.getAnio()));
            preparedStatement.setString(3, seccion.getNombreSeccion());
            preparedStatement.executeUpdate();
        }
    }

    public void actualizarSeccion(SeccionActualizarRequest seccion) throws SQLException{
        String sqlNuevaSeccion = "UPDATE Seccion SET idGrado = ?, anio = ?, seccion = ? WHERE idSeccion = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlNuevaSeccion)){
            preparedStatement.setInt(1, Integer.parseInt(seccion.getIdGrado()));
            preparedStatement.setInt(2, Integer.parseInt(seccion.getAnio()));
            preparedStatement.setString(3, seccion.getNombreSeccion());
            preparedStatement.setInt(4,Integer.parseInt(seccion.getIdSeccion()));
            preparedStatement.executeUpdate();
        }
    }

    public Optional<Seccion> obtenerSeccion(int idSeccion) throws SQLException{
        Optional<Seccion> seccion;
        String sqlSolicitarSecciones = "SELECT * FROM Seccion WHERE idSeccion = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarSecciones)) {
            preparedStatement.setInt(1,idSeccion);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            seccion = Optional.of(crearSeccion(resultSet));
        }
        return seccion;
    }

    public boolean existeSeccion(SeccionRequest seccion) throws SQLException {
        String sqlConsulta = "SELECT COUNT(*) FROM Seccion WHERE idGrado = ? AND anio = ? AND seccion = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlConsulta)) {

            preparedStatement.setInt(1, Integer.parseInt(seccion.getIdGrado()));
            preparedStatement.setInt(2, Integer.parseInt(seccion.getAnio()));
            preparedStatement.setString(3, seccion.getNombreSeccion());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int conteo = resultSet.getInt(1);
                    return conteo > 0;
                }
                return false;
            }
        }
    }
    public Seccion crearSeccion(ResultSet resultSet) throws SQLException{
        int idSeccion = resultSet.getInt("idSeccion");
        int idGrado = resultSet.getInt("idGrado");
        int anio = resultSet.getInt("anio");
        char seccion = resultSet.getString("seccion").charAt(0);
        return new Seccion(idSeccion,idGrado,anio,seccion);
    }
}
