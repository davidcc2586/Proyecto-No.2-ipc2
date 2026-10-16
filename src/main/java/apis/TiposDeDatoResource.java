package apis;

import clasesAuxiliares.EnumServicio;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objetos.enums.EnumSelectResponse;

import java.util.List;

@Path("enum")
@Produces(MediaType.APPLICATION_JSON)
public class TiposDeDatoResource {
    @GET
    @Path("selects/{tipoEnum}")
    public Response tiposEstados(@PathParam("tipoEnum") String tipoEnum){
        EnumServicio enumServicio = new EnumServicio();
        List<EnumSelectResponse> enums = enumServicio.clasificarTipoEnum(tipoEnum);
        return Response.ok(enums).build();
    }

}
