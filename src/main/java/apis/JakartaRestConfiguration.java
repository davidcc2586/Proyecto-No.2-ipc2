package apis;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

@ApplicationPath("api")
public class JakartaRestConfiguration extends ResourceConfig {
    public JakartaRestConfiguration() {
        packages("apis").property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
    }
}