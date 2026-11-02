package apis;

import clasesAuxiliares.SessionTokenService;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import objetos.enums.RolUsuario;

import java.io.IOException;
import java.util.Set;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class TokenAuthenticationFilter implements ContainerRequestFilter {
    public static final String CLAIMS_PROPERTY = TokenAuthenticationFilter.class.getName() + ".claims";

    private static final String LOGIN_PATH = "inicioSesion/acceso";
    private static final Set<String> PUBLIC_PATHS = Set.of(LOGIN_PATH, "inicioSesion/recuperarContraseña", "inicioSesion/crearCuentaEstudiante");

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        if (PUBLIC_PATHS.contains(path)) {
            return;
        }

        String authorization = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            abort(requestContext, Response.Status.UNAUTHORIZED, "Se requiere un token de sesión válido.");
            return;
        }

        SessionTokenService.Claims claims = SessionTokenService.verify(authorization.substring(7).trim());
        if (claims == null) {
            abort(requestContext, Response.Status.UNAUTHORIZED, "El token de sesión no es válido o ha expirado.");
            return;
        }

        Set<RolUsuario> rolesPermitidos = rolesPermitidos(path);
        if (rolesPermitidos != null && !rolesPermitidos.contains(claims.role())) {
            abort(requestContext, Response.Status.FORBIDDEN, "Tu rol no tiene permiso para consultar este reporte.");
            return;
        }
        requestContext.setProperty(CLAIMS_PROPERTY, claims);
    }

    private Set<RolUsuario> rolesPermitidos(String path) {
        if (path.startsWith("reportes/pagos/")) {
            return Set.of(RolUsuario.ADMIN, RolUsuario.SUPERADMIN);
        }
        if (path.equals("reportes/ingresos")) {
            return Set.of(RolUsuario.SUPERADMIN);
        }
        if (path.equals("reportes/biblioteca/morosidad")) {
            return Set.of(RolUsuario.BIBLIOTECARIO, RolUsuario.ADMIN, RolUsuario.SUPERADMIN);
        }
        return null;
    }

    private void abort(ContainerRequestContext requestContext, Response.Status status, String message) {
        requestContext.abortWith(Response.status(status).type("application/json").entity("{\"error\":\"" + message + "\"}").build());
    }
}
