package apis;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objetos.objetosDB.FacturaDB;

import java.sql.SQLException;

@Path("factura")
@Produces(MediaType.APPLICATION_JSON)
public class FacturaResource {

    @GET
    @Path("todos")
    public Response todasLasFacturas() {
        try {
            FacturaDB facturaDB = new FacturaDB();
            return Response.ok(facturaDB.todosLasFacturas()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"No se pudieron consultar las facturas\"}").build();
        }
    }
}
