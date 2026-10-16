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

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BibliotecaServicio {
    public List<Libro> catalogo() throws SQLException{
        LibroDB libroDB = new LibroDB();
        return libroDB.todosLosLibros();
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
        } catch (NumberFormatException e) {
            throw new ValidacionException("Formato de año/cantidad equivocado");
        }
        LibroDB libroDB = new LibroDB();
        libroDB.actualizarLibro(libro);
    }

    public List<PrestamoLibroResponse> prestamosLibro(String estadoPrestamo) throws SQLException{
        PrestamoLibroDB prestamoLibroDB = new PrestamoLibroDB();
        EstadoPrestamoLibro estadoPrestamoLibro = EstadoPrestamoLibro.valueOf(estadoPrestamo);
        return prestamoLibroDB.prestamosLibro(LocalDate.now(),false,estadoPrestamoLibro);
    }

    public void registrarNuevoPrestamoLibro(PrestamoLibroRequest prestamoLibroRequest) throws SQLException{
        //revisar qui si quedan unidades para prestar
        PrestamoLibroDB prestamoLibroDB = new PrestamoLibroDB();
        prestamoLibroDB.registrarNuevoPrestamoLibro(prestamoLibroRequest);
    }

    public void registrarDevolucionLibro(PrestamoLibroDevolucionRequest prestamo) throws SQLException{
        PrestamoLibroDB prestamoLibroDB = new PrestamoLibroDB();
        prestamoLibroDB.registrarDevolucionLibro(prestamo);
    }
}
