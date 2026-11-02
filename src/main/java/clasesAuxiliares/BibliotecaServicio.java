package clasesAuxiliares;

import objetos.enums.EstadoPrestamoLibro;
import objetos.exceptions.ValidacionException;
import objetos.objetosDB.LibroDB;
import objetos.objetosDB.PrestamoLibroDB;
import objetos.objetosRequest.LibroActualizarRequest;
import objetos.objetosRequest.LibroRequest;
import objetos.objetosRequest.PrestamoLibroDevolucionRequest;
import objetos.objetosRequest.PrestamoLibroRequest;
import objetos.objetosResponse.Libro;
import objetos.objetosResponse.LibroSelectResponse;
import objetos.objetosResponse.PrestamoLibro;
import objetos.objetosResponse.PrestamoLibroResponse;
import objetos.objetosResponse.PaginaResponse;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BibliotecaServicio {
    public List<Libro> catalogo() throws SQLException{
        LibroDB libroDB = new LibroDB();
        return libroDB.todosLosLibros();
    }

    public PaginaResponse<Libro> catalogoPaginado(int pagina, int tamano) throws SQLException {
        LibroDB libroDB = new LibroDB();
        return libroDB.librosPaginados(pagina, tamano);
    }

    public PaginaResponse<Libro> catalogoDisponiblePaginado(int pagina, int tamano) throws SQLException {
        LibroDB libroDB = new LibroDB();
        return libroDB.librosDisponiblesPaginados(pagina, tamano);
    }

    public List<LibroSelectResponse> selectsLibros() throws SQLException{
        LibroDB libroDB = new LibroDB();
        return libroDB.selectLibros();
    }

    public void registrarNuevoLibro(LibroRequest libroRequest) throws SQLException,ValidacionException {
        LibroDB libroDB = new LibroDB();
        try {
            Integer.parseInt(libroRequest.getAnio());
            if (Integer.parseInt(libroRequest.getCantidad()) < 0){
                throw new ValidacionException("La Cantidad tiene que ser positiva");
            }
        } catch (NumberFormatException e) {
            throw new ValidacionException("Formato de año/cantidad equivocado");
        }
        libroDB.ingresarNuevoLibro(libroRequest);
    }

    public void eliminarLibro(int idLibro)throws SQLException{
        LibroDB libroDB = new LibroDB();
        libroDB.eliminarLibro(idLibro);
    }

    public void desactivarActivarLibro(int idLibro) throws SQLException{
        LibroDB libroDB = new LibroDB();
        libroDB.desactivarActivarLibro(idLibro);
    }
    public void actualizarLibro(LibroActualizarRequest libro) throws SQLException,ValidacionException{
        try {
            Integer.parseInt(libro.getAnio());
            if (Integer.parseInt(libro.getCantidad()) < 0) {
                throw new ValidacionException("La cantidad de existencias no puede ser negativa.");
            }
        } catch (NumberFormatException e) {
            throw new ValidacionException("Formato de año/cantidad equivocado");
        }
        LibroDB libroDB = new LibroDB();
        libroDB.actualizarLibro(libro);
    }

    public List<PrestamoLibroResponse> prestamosLibro(String estadoPrestamo,boolean filtrarMorosos) throws SQLException{
        PrestamoLibroDB prestamoLibroDB = new PrestamoLibroDB();
        EstadoPrestamoLibro estadoPrestamoLibro = EstadoPrestamoLibro.valueOf(estadoPrestamo);
        return prestamoLibroDB.prestamosLibro(LocalDate.now(),filtrarMorosos,estadoPrestamoLibro);
    }

    public PaginaResponse<PrestamoLibroResponse> prestamosLibroPaginados(String estadoPrestamo, boolean filtrarMorosos, int pagina, int tamano) throws SQLException {
        PrestamoLibroDB prestamoLibroDB = new PrestamoLibroDB();
        EstadoPrestamoLibro estadoPrestamoLibro = EstadoPrestamoLibro.valueOf(estadoPrestamo);
        return prestamoLibroDB.prestamosLibroPaginados(LocalDate.now(), filtrarMorosos, estadoPrestamoLibro, pagina, tamano);
    }



    public void registrarNuevoPrestamoLibro(PrestamoLibroRequest prestamoLibroRequest) throws SQLException,ValidacionException{
        LibroDB libroDB = new LibroDB();
        if (libroDB.cantidadLibrosDisponibles(Integer.parseInt(prestamoLibroRequest.getIdLibro())) <= 0){
            throw new ValidacionException("Cantidad de libros insuficiente");
        }
        PrestamoLibroDB prestamoLibroDB = new PrestamoLibroDB();
        prestamoLibroDB.registrarNuevoPrestamoLibro(prestamoLibroRequest);
    }

    public void registrarDevolucionLibro(PrestamoLibroDevolucionRequest prestamo) throws SQLException{
        PrestamoLibroDB prestamoLibroDB = new PrestamoLibroDB();
        prestamoLibroDB.registrarDevolucionLibro(prestamo);
    }


    public List<PrestamoLibroResponse> prestamosLibroEstudiante(int idEstudiante,String estadoPrestamo, boolean filtroMorosos) throws SQLException{
        PrestamoLibroDB prestamoLibroDB = new PrestamoLibroDB();
        EstadoPrestamoLibro estadoPrestamoLibro = EstadoPrestamoLibro.valueOf(estadoPrestamo);
        return prestamoLibroDB.prestamosLibroEstudiante(idEstudiante,LocalDate.now(),filtroMorosos,estadoPrestamoLibro);
    }

    public PaginaResponse<PrestamoLibroResponse> prestamosLibroEstudiantePaginados(int idEstudiante, String estadoPrestamo, boolean filtroMorosos, int pagina, int tamano) throws SQLException {
        PrestamoLibroDB prestamoLibroDB = new PrestamoLibroDB();
        EstadoPrestamoLibro estadoPrestamoLibro = EstadoPrestamoLibro.valueOf(estadoPrestamo);
        return prestamoLibroDB.prestamosLibroEstudiantePaginados(idEstudiante, LocalDate.now(), filtroMorosos, estadoPrestamoLibro, pagina, tamano);
    }

    public Libro obtenerLibro(int idLIbro) throws SQLException{
        LibroDB libroDB = new LibroDB();
        return libroDB.obtenerLibro(idLIbro).get();
    }
}
