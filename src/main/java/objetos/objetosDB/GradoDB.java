package objetos.objetosDB;

import objetos.enums.GradoColegio;
import objetos.objetosRequest.GradoActualizarRequest;
import objetos.objetosRequest.GradoRequest;
import objetos.objetosResponse.AsignacionEstudiantes;
import objetos.objetosResponse.Grado;
import objetos.objetosResponse.Seccion;
import objetos.objetosResponse.PaginaResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GradoDB {

    public List<Grado> todosLosGrados() throws SQLException {
        List<Grado> gradoResponses = new ArrayList<>();
        String sqlSolicitarGrados = "SELECT * FROM Grado";

        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarGrados);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()){
                gradoResponses.add(crearGrado(resultSet));
            }
        }

        return gradoResponses;
    }

    public PaginaResponse<Grado> gradosPaginados(int pagina, int tamano) throws SQLException {
        int offset = new objetos.Paginacion().calcularDesplazamiento(pagina, tamano);
        List<Grado> grados = new ArrayList<>();
        int totalElementos = 0;
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement conteo = connection.prepareStatement("SELECT COUNT(*) FROM Grado")) {
            try (ResultSet resultado = conteo.executeQuery()) {
                if (resultado.next()) {
                    totalElementos = resultado.getInt(1);
                }
            }
            try (PreparedStatement consulta = connection.prepareStatement("SELECT * FROM Grado ORDER BY idGrado LIMIT ? OFFSET ?")) {
                consulta.setInt(1, tamano);
                consulta.setInt(2, offset);
                try (ResultSet resultado = consulta.executeQuery()) {
                    while (resultado.next()) {
                        grados.add(crearGrado(resultado));
                    }
                }
            }
        }
        return new PaginaResponse<>(grados, pagina, tamano, totalElementos);
    }
    public void ingresarNuevoGrado(GradoRequest grado) throws SQLException{
        if(grado.getIdCarrera().equalsIgnoreCase("0")){
            String sqlIngresarGrado = "INSERT INTO Grado(nombreGrado, grado) VALUES(?,?)";
            try (Connection connection = ConnectionDB.getConexion();
                 PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarGrado)){
                preparedStatement.setString(1, grado.getNombreGrado());
                preparedStatement.setString(2, grado.getGradoColegio());
                preparedStatement.executeUpdate();
            }
            return;
        }
        String sqlIngresarGrado = "INSERT INTO Grado(nombreGrado, grado, idCarrera) VALUES(?,?,?)";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarGrado)){
            preparedStatement.setString(1, grado.getNombreGrado());
            preparedStatement.setString(2, grado.getGradoColegio());
            preparedStatement.setInt(3, Integer.parseInt(grado.getIdCarrera()));
            preparedStatement.executeUpdate();
        }
    }

    public void actualizarGrado(GradoActualizarRequest grado) throws SQLException{
        String sqlIngresarGrado = "UPDATE Grado SET nombreGrado = ?,grado = ?, idCarrera = ? WHERE idGrado = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarGrado)){
            preparedStatement.setString(1, grado.getNombreGrado());
            preparedStatement.setString(2, grado.getGradoColegio());
            preparedStatement.setInt(3, Integer.parseInt(grado.getIdCarrera()));
            preparedStatement.executeUpdate();
        }
    }

    public Grado gradoEstudiante(int idEstudiante, int anio) throws SQLException{
        List<AsignacionEstudiantes> asignaciones = new ArrayList<>();
        try (Connection connection = ConnectionDB.getConexion()){
            String sql = "SELECT * FROM AsignacionEstudiantes where  idEstudiante = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,idEstudiante);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                asignaciones.add(new AsignacionEstudiantes(resultSet.getInt("idSeccion"), idEstudiante));
            }
        }
        if (asignaciones.isEmpty()){
            throw new SQLException("Usuario no encontrado.");
        }

        Seccion seccion = null;
        try (Connection connection = ConnectionDB.getConexion()){
            String sql = "select * from Seccion where  idSeccion = ? and anio = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (AsignacionEstudiantes asinacion : asignaciones){
                preparedStatement.setInt(1,asinacion.getIdSeccion());
                preparedStatement.setInt(2,anio);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    SeccionDB seccionDB = new SeccionDB();
                    seccion = seccionDB.crearSeccion(resultSet);
                    break;
                }
            }
        }
        if (seccion == null){
            throw new SQLException("Usuario no encontrado.");
        }

        try (Connection connection = ConnectionDB.getConexion()){
            String sql = "SELECT * FROM Grado where idGrado = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,seccion.getIdGrado());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return crearGrado(resultSet);
            }else {
                throw new SQLException("Usuario no encontrado.");
            }
        }
    }

    public Optional<Grado> obtenerGrado(int idGrado) throws SQLException{
        String sqlIngresarGrado = "SELECT * FROM Grado WHERE idGrado = ?";
        Optional<Grado> grado = Optional.empty();
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarGrado)){
            preparedStatement.setInt(1, idGrado);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            grado = Optional.of(crearGrado(resultSet));
        }
        return grado;
    }

    private Grado crearGrado(ResultSet resultSet) throws SQLException{
        int idGrado = resultSet.getInt("idGrado");
        String nombreGrado = resultSet.getString("nombreGrado");
        GradoColegio gradoColegio = GradoColegio.valueOf(resultSet.getString("grado"));
        int idCarrera = resultSet.getInt("idCarrera");
        return new Grado(idGrado, nombreGrado, gradoColegio, idCarrera);
    }
}
