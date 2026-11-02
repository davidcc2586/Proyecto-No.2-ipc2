package objetos.objetosDB;

import objetos.enums.EstadoEstudiante;
import objetos.exceptions.ValidacionException;
import objetos.objetosRequest.EstudianteActualizarRequest;
import objetos.objetosResponse.Estudiante;
import objetos.objetosResponse.EstudianteDatosResponse;
import objetos.objetosResponse.PaginaResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EstudianteDB {

    public EstudianteDatosResponse datosEstudiante(int idEstudiante) throws SQLException{
        Estudiante estudiante = obtenerEstudiante(idEstudiante).get();
        return new EstudianteDatosResponse(estudiante.getCui(), estudiante.getNombre(), estudiante.getApellido(),estudiante.getFechaNacimiento(), estudiante.getTipoSangre(), estudiante.getAlergia(), estudiante.getNombreEncargado(), estudiante.getTelefonoEncargado(), estudiante.getCorreoEncargado(), estudiante.getAnioInscripcion(),estudiante.getEstadoEstudiante());
    }
    public List<Estudiante> estudiantesSeccion(int idSeccion) throws SQLException{
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = "SELECT * FROM Estudiante e JOIN AsignacionEstudiantes ae ON e.idEstudiante = ae.idEstudiante WHERE ae.idSeccion = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,idSeccion);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                estudiantes.add(crearEstudiante(resultSet));
            }
        }
        return estudiantes;
    }

    public PaginaResponse<Estudiante> estudiantesSeccionPaginados(int idSeccion, int pagina, int tamano) throws SQLException {
        int offset = new objetos.Paginacion().calcularDesplazamiento(pagina, tamano);
        List<Estudiante> estudiantes = new ArrayList<>();
        int totalElementos = 0;
        String sqlConteo = "SELECT COUNT(*) FROM Estudiante estudiante JOIN AsignacionEstudiantes aisgnacion ON estudiante.idEstudiante = aisgnacion.idEstudiante WHERE aisgnacion.idSeccion = ?";
        String sql = "SELECT * FROM Estudiante estudiante JOIN AsignacionEstudiantes aisgnacion ON estudiante.idEstudiante = aisgnacion.idEstudiante WHERE aisgnacion.idSeccion = ? ORDER BY estudiante.idEstudiante LIMIT ? OFFSET ?";

        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement conteo = connection.prepareStatement(sqlConteo)) {
            conteo.setInt(1, idSeccion);
            try (ResultSet resultado = conteo.executeQuery()) {
                if (resultado.next()) {
                    totalElementos = resultado.getInt(1);
                }
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, idSeccion);
                preparedStatement.setInt(2, tamano);
                preparedStatement.setInt(3, offset);
                try (ResultSet resultado = preparedStatement.executeQuery()) {
                    while (resultado.next()) {
                        estudiantes.add(crearEstudiante(resultado));
                    }
                }
            }
        }
        return new PaginaResponse<>(estudiantes, pagina, tamano, totalElementos);
    }

    public PaginaResponse<Estudiante> estudiantesPorEstadoPaginados(EstadoEstudiante estadoEstudiante, int pagina, int tamano) throws SQLException {
        int offset = new objetos.Paginacion().calcularDesplazamiento(pagina, tamano);
        List<Estudiante> estudiantes = new ArrayList<>();
        int totalElementos = 0;
        String sqlConteo = "SELECT COUNT(*) FROM Estudiante WHERE estado = ?";
        String sql = "SELECT * FROM Estudiante WHERE estado = ? ORDER BY idEstudiante LIMIT ? OFFSET ?";

        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement conteo = connection.prepareStatement(sqlConteo)) {
            conteo.setString(1, estadoEstudiante.name());
            try (ResultSet resultado = conteo.executeQuery()) {
                if (resultado.next()) {
                    totalElementos = resultado.getInt(1);
                }
            }

            try (PreparedStatement consulta = connection.prepareStatement(sql)) {
                consulta.setString(1, estadoEstudiante.name());
                consulta.setInt(2, tamano);
                consulta.setInt(3, offset);
                try (ResultSet resultado = consulta.executeQuery()) {
                    while (resultado.next()) {
                        estudiantes.add(crearEstudiante(resultado));
                    }
                }
            }
        }
        return new PaginaResponse<>(estudiantes, pagina, tamano, totalElementos);
    }
    public List<Estudiante> obtenerEstudiantesSegunEstado(EstadoEstudiante estadoEstudiante) throws SQLException {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = "SELECT * FROM Estudiante WHERE estado = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,estadoEstudiante.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                estudiantes.add(crearEstudiante(resultSet));
            }
        }
        return estudiantes;
    }

    public Optional<Estudiante> obtenerEstudiante(int idEstudiante) throws SQLException{
        Optional<Estudiante> estudiante = Optional.empty();
        String sqlSolicitarEstudiantes = "SELECT * From Estudiante  WHERE idEstudiante = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarEstudiantes)){
            preparedStatement.setInt(1,idEstudiante);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                estudiante = Optional.of(crearEstudiante(resultSet));
            }
        }
        return estudiante;
    }

    public void ingresarNuevoEstudiante(Estudiante estudiante) throws SQLException{
        String sqlIngresarEstudiante = "insert into Estudiante(cui, nombre, apellido, fechaNacimiento, usuario, clave, anioInscripcion, tipoSangre, alergias, nombreEncargado, telefonoEncargado, correoEncargado) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarEstudiante)){
            preparedStatement.setString(1, estudiante.getCui());
            preparedStatement.setString(2, estudiante.getNombre());
            preparedStatement.setString(3, estudiante.getApellido());
            preparedStatement.setDate(4,java.sql.Date.valueOf(estudiante.getFechaNacimiento()));
            preparedStatement.setString(5, estudiante.getUsuario());
            preparedStatement.setString(6, estudiante.getClave());
            preparedStatement.setInt(7, estudiante.getAnioInscripcion());
            preparedStatement.setString(8, estudiante.getTipoSangre());
            preparedStatement.setString(9, estudiante.getAlergia());
            preparedStatement.setString(10, estudiante.getNombreEncargado());
            preparedStatement.setString(11, estudiante.getTelefonoEncargado());
            preparedStatement.setString(12, estudiante.getCorreoEncargado());
            preparedStatement.executeUpdate();
        }
    }

    public void cambiarEstadoEstudiante(int idEstudiante,EstadoEstudiante estadoEstudiante) throws SQLException{
        String sqlCambiarEstado = "UPDATE Estudiante SET estado = ? WHERE idEstudiante = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlCambiarEstado)){
            preparedStatement.setString(1,estadoEstudiante.name());
            preparedStatement.setInt(2,idEstudiante);
            preparedStatement.executeUpdate();
        }
    }

    public void actualizarEstudiante(EstudianteActualizarRequest estudiante) throws SQLException, ValidacionException{
        StringBuilder sql = new StringBuilder("UPDATE Estudiante SET ");
        List<Object> parametros = new ArrayList<>();
        boolean hayCampos = false;

        if (estudiante.getAlergia() != null) {
            sql.append("alergias = ?");
            parametros.add(estudiante.getAlergia());
            hayCampos = true;
        }
        if (estudiante.getNombreEncargado() != null ) {
            if (hayCampos) sql.append(", ");
            sql.append("nombreEncargado = ?");
            parametros.add(estudiante.getNombreEncargado());
            hayCampos = true;
        }
        if (estudiante.getTelefonoEncargado() != null) {
            if (hayCampos) sql.append(", ");
            sql.append("telefonoEncargado = ?");
            parametros.add(Double.parseDouble(estudiante.getTelefonoEncargado()));
            hayCampos = true;
        }
        if (estudiante.getCorreoEncargado() != null) {
            if (hayCampos) sql.append(", ");
            sql.append("correoEncargado = ?");
            parametros.add(estudiante.getCorreoEncargado());
            hayCampos = true;
        }
        if (!hayCampos) {
            throw new ValidacionException("No se proporcionó ningun campo para actualizar.");
        }

        sql.append(" WHERE idEstudiante = ?");
        parametros.add(estudiante.getIdEstudiante());

        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < parametros.size(); i++) {
                preparedStatement.setObject(i + 1, parametros.get(i));
            }
            preparedStatement.executeUpdate();
        }
    }

    private Estudiante crearEstudiante(ResultSet resultSet) throws SQLException {
        int idEstudiante = resultSet.getInt("idEstudiante");
        String cui = resultSet.getString("cui");
        String nombre = resultSet.getString("nombre");
        String apellido = resultSet.getString("apellido");
        LocalDate fechaNacimiento = resultSet.getDate("fechaNacimiento").toLocalDate();
        String usuario = resultSet.getString("usuario");
        String clave = resultSet.getString("clave");
        int anioInscripcion = resultSet.getInt("anioInscripcion");
        EstadoEstudiante estadoEstudiante = EstadoEstudiante.valueOf(resultSet.getString("estado"));
        String tipoSangre = resultSet.getString("tipoSangre");
        String nombreEncargado = resultSet.getString("nombreEncargado");
        String telefonoEncargado = resultSet.getString("telefonoEncargado");
        String alergia = resultSet.getString("alergias");
        String correoEncargado = resultSet.getString("correoEncargado");
        return new Estudiante(idEstudiante,cui,nombre,apellido,fechaNacimiento,usuario,clave,anioInscripcion,estadoEstudiante,tipoSangre,alergia,nombreEncargado,telefonoEncargado,correoEncargado);
    }
}
