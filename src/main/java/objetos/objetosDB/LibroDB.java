package objetos.objetosDB;

import objetos.enums.Estado;
import objetos.enums.EstadoPrestamoLibro;
import objetos.objetosRequest.LibroActualizarRequest;
import objetos.objetosRequest.LibroRequest;
import objetos.objetosResponse.Libro;
import objetos.objetosResponse.LibroSelectResponse;
import objetos.objetosResponse.PaginaResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibroDB {

    public void eliminarLibro(int idLibro) throws SQLException{

        int cantidadLibrosPrestados = 0;

        try (Connection connection = ConnectionDB.getConexion()){
            String sql = "SELECT count(*) FROM PrestamoLibro WHERE idLibro = ? AND estadoPrestamo = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,idLibro);
            preparedStatement.setString(2, EstadoPrestamoLibro.PRESTADO.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                cantidadLibrosPrestados = resultSet.getInt(1);
            }
        }

        if (cantidadLibrosPrestados != 0){
            throw new SQLException("La cantidad de prestamos actuales de este libro es de: " + cantidadLibrosPrestados);
        }
        try (Connection connection = ConnectionDB.getConexion()){
            String sql = "DELETE FROM Libro WHERE idLibro = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idLibro);
            preparedStatement.executeUpdate();
        }
    }

    public void desactivarActivarLibro(int idLibro) throws SQLException{

        Estado estado = null;
        try (Connection connection = ConnectionDB.getConexion()){
            String sql = "SELECT estado FROM Libro WHERE idLibro = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,idLibro);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                estado =  Estado.valueOf(resultSet.getString("estado"));
            }
        }
        if (estado == Estado.ACTIVO){
            estado = Estado.INACTIVO;
        } else {
            estado = Estado.ACTIVO;
        }
        try (Connection connection = ConnectionDB.getConexion()){
            String sql = "UPDATE Libro SET estado = ?  WHERE  idLibro = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, estado.name());
            preparedStatement.setInt(2, idLibro);
            preparedStatement.executeUpdate();
        }
    }

    public List<Libro> todosLosLibros() throws SQLException {
        List<Libro> libroResponses = new ArrayList<>();
        String sqlSolicitarLibros = "SELECT * FROM Libro";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarLibros);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()){
                libroResponses.add(crearLibro(resultSet));
            }
        }
        return libroResponses;
    }

    public PaginaResponse<Libro> librosPaginados(int pagina, int tamano) throws SQLException {
        int offset = new objetos.Paginacion().calcularDesplazamiento(pagina, tamano);
        List<Libro> libros = new ArrayList<>();
        int totalElementos = 0;
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement conteo = connection.prepareStatement("SELECT COUNT(*) FROM Libro")) {
            try (ResultSet resultado = conteo.executeQuery()) {
                if (resultado.next()) {
                    totalElementos = resultado.getInt(1);
                }
            }
            try (PreparedStatement consulta = connection.prepareStatement("SELECT * FROM Libro ORDER BY idLibro LIMIT ? OFFSET ?")) {
                consulta.setInt(1, tamano);
                consulta.setInt(2, offset);
                try (ResultSet resultado = consulta.executeQuery()) {
                    while (resultado.next()) {
                        libros.add(crearLibro(resultado));
                    }
                }
            }
        }
        return new PaginaResponse<>(libros, pagina, tamano, totalElementos);
    }

    public PaginaResponse<Libro> librosDisponiblesPaginados(int pagina, int tamano) throws SQLException {
        int offset = new objetos.Paginacion().calcularDesplazamiento(pagina, tamano);
        List<Libro> libros = new ArrayList<>();
        int totalElementos = 0;
        String filtro = " WHERE estado = ? AND cantidad > 0";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement conteo = connection.prepareStatement("SELECT COUNT(*) FROM Libro" + filtro)) {
            conteo.setString(1, Estado.ACTIVO.name());
            try (ResultSet resultado = conteo.executeQuery()) {
                if (resultado.next()) {
                    totalElementos = resultado.getInt(1);
                }
            }
            String sql= "SELECT * FROM Libro" + filtro + " ORDER BY titulo, idLibro LIMIT ? OFFSET ?";
            try (PreparedStatement consulta = connection.prepareStatement(sql)) {
                consulta.setString(1, Estado.ACTIVO.name());
                consulta.setInt(2, tamano);
                consulta.setInt(3, offset);
                try (ResultSet resultado = consulta.executeQuery()) {
                    while (resultado.next()) {
                        libros.add(crearLibro(resultado));
                    }
                }
            }
        }
        return new PaginaResponse<>(libros, pagina, tamano, totalElementos);
    }

    public Optional<Libro> obtenerLibro(int idLIbro) throws SQLException{
        Optional<Libro> libro = Optional.empty();
        String sqlSolicitarLibro = "SELECT * FROM Libro WHERE idLibro = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarLibro)){
            preparedStatement.setInt(1,idLIbro);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                libro = Optional.of(crearLibro(resultSet));
            }
        }
        return libro;
    }

    public void ingresarNuevoLibro(LibroRequest libro) throws SQLException{
        String sqlIngresarLibro = "insert into Libro(autor, anio, titulo, editorial, cantidad) VALUES(?,?,?,?,?)";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarLibro)){
            preparedStatement.setString(1,libro.getAutor());
            preparedStatement.setInt(2,Integer.parseInt(libro.getAnio()));
            preparedStatement.setString(3,libro.getTitulo());
            preparedStatement.setString(4,libro.getEditorial());
            preparedStatement.setInt(5,Integer.parseInt(libro.getCantidad()));
            preparedStatement.executeUpdate();
        }
    }

    public void actualizarLibro(LibroActualizarRequest libro) throws SQLException{
        String sqlIngresarLibro = "UPDATE Libro set autor = ?,anio = ?,titulo = ?,editorial = ?, cantidad = ? WHERE idLibro = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarLibro)){
            preparedStatement.setString(1,libro.getAutor());
            preparedStatement.setInt(2,Integer.parseInt(libro.getAnio()));
            preparedStatement.setString(3,libro.getTitulo());
            preparedStatement.setString(4,libro.getEditorial());
            preparedStatement.setInt(5,Integer.parseInt(libro.getCantidad()));
            preparedStatement.setInt(6,Integer.parseInt(libro.getIdLibro()));
            preparedStatement.executeUpdate();
        }
    }

    public List<LibroSelectResponse> selectLibros() throws SQLException{
        String sql = "SELECT idLibro,titulo FROM Libro WHERE estado = ?";
        List<LibroSelectResponse> librosResumen = new ArrayList<>();
        try (Connection connection = ConnectionDB.getConexion()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,Estado.ACTIVO.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                librosResumen.add(new LibroSelectResponse(resultSet.getInt("idLibro"), resultSet.getString("titulo")));
            }
        }
        return librosResumen;
    }

    public int cantidadLibrosDisponibles(int idLibro) throws SQLException{
        String sql = "SELECT cantidad FROM Libro WHERE idLibro = ? AND estado = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,idLibro);
            preparedStatement.setString(2, Estado.ACTIVO.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        }
    }
    private Libro crearLibro(ResultSet resultSet) throws SQLException{
        int idLibro = resultSet.getInt("idLibro");
        Estado estado = Estado.valueOf(resultSet.getString("estado"));
        String autor = resultSet.getString("autor");
        int anio = resultSet.getInt("anio");
        String titulo = resultSet.getString("titulo");
        String editorial = resultSet.getString("editorial");
        int cantidad = resultSet.getInt("cantidad");
        return new Libro(idLibro, estado, autor, anio, titulo, editorial, cantidad);
    }
}
