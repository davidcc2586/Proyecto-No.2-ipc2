package apis;

import clasesAuxiliares.BibliotecaServicio;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objetos.exceptions.ValidacionException;
import objetos.objetosRequest.LibroActualizarRequest;
import objetos.objetosRequest.LibroRequest;
import objetos.objetosRequest.PrestamoLibroDevolucionRequest;
import objetos.objetosRequest.PrestamoLibroRequest;

import java.sql.SQLException;

@Path("biblioteca")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BibliotecaResource {

    @GET
    @Path("datosLibro/{idLibro}")
    public Response datosLibro(@PathParam("idLibro") int idLibro){
        try {
            BibliotecaServicio bibliotecaServicio = new BibliotecaServicio();
            return Response.ok(bibliotecaServicio.obtenerLibro(idLibro)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

    @GET
    @Path("catalogo")
    public Response catalogoLibros(){
        try {
            BibliotecaServicio bibliotecaServicio = new BibliotecaServicio();
            return Response.ok(bibliotecaServicio.catalogo()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }


    @GET
    @Path("selects")
    public Response selectLibros(){
        try {
            BibliotecaServicio bibliotecaServicio = new BibliotecaServicio();
            return Response.ok(bibliotecaServicio.selectsLibros()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

    @POST
    @Path("registraLibro")
    public Response registrarLibro(LibroRequest libroRequest){
        try {
            BibliotecaServicio bibliotecaServicio = new BibliotecaServicio();
            bibliotecaServicio.registrarNuevoLibro(libroRequest);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        } catch (ValidacionException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @DELETE
    @Path("eliminarLibro/{idLibro}")
    public Response eliminarLibro( @PathParam("idLibro") int idLibro){
        try {
            BibliotecaServicio bibliotecaServicio = new BibliotecaServicio();
            bibliotecaServicio.eliminarLibro(idLibro);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

    @PUT
    @Path("desactivarActivar/{idLibro}")
    public Response desactivarActivarLibro( @PathParam("idLibro") int idLibro){
        try {
            BibliotecaServicio bibliotecaServicio = new BibliotecaServicio();
            bibliotecaServicio.desactivarActivarLibro(idLibro);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

    @PUT
    @Path("actualizar")
    public Response actualizarLibro(LibroActualizarRequest libroActualizarRequest){
        try {
            BibliotecaServicio bibliotecaServicio = new BibliotecaServicio();
            bibliotecaServicio.actualizarLibro(libroActualizarRequest);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        } catch (ValidacionException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @GET
    @Path("prestamoLibro/{estado}/{filtroMoroso}")
    public Response prestamosLibro(@PathParam("estado") String estadoPrestamo,@PathParam("filtroMoroso") boolean filtro){
        try {
            BibliotecaServicio bibliotecaServicio = new BibliotecaServicio();
            return Response.ok(bibliotecaServicio.prestamosLibro(estadoPrestamo, filtro)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

    @PATCH
    @Path("RegistrarPrestamo")
    public Response registrarNuevoPrestamoLibro(PrestamoLibroRequest prestamoLibroRequest){
        try {
            BibliotecaServicio bibliotecaServicio = new BibliotecaServicio();
            bibliotecaServicio.registrarNuevoPrestamoLibro(prestamoLibroRequest);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        } catch (ValidacionException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @PUT
    @Path("RegistrarDevolucio")
    public Response registrarNuevoPrestamoLibro(PrestamoLibroDevolucionRequest prestamo){
        try {
            BibliotecaServicio bibliotecaServicio = new BibliotecaServicio();
            bibliotecaServicio.registrarDevolucionLibro(prestamo);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }


    @GET
    @Path("prestamoLibroEstudiante/{estado}/{idEstudiante}/{filtroMoroso}")
    public Response prestamosLibro(@PathParam("estado") String estadoPrestamo,@PathParam("idEstudiante") int idEstudiante,@PathParam("filtroMoroso") boolean filtro){
        try {
            BibliotecaServicio bibliotecaServicio = new BibliotecaServicio();
            return Response.ok(bibliotecaServicio.prestamosLibroEstudiante(idEstudiante, estadoPrestamo, filtro)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }
}
