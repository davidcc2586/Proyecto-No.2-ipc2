package objetos.objetosRequest;

public class GradoActualizarRequest {

    public String nombreGrado;
    private String gradoColegio;
    private String idCarrera;
    private String idGrado;

    public GradoActualizarRequest(){

    }
    public GradoActualizarRequest(String nombreGrado, String gradoColegio, String idCarrera, String idGrado) {
        this.nombreGrado = nombreGrado;
        this.gradoColegio = gradoColegio;
        this.idCarrera = idCarrera;
        this.idGrado = idGrado;
    }

    public String getNombreGrado() {
        return nombreGrado;
    }

    public String getGradoColegio() {
        return gradoColegio;
    }

    public String getIdCarrera() {
        return idCarrera;
    }

    public String getIdGrado() {
        return idGrado;
    }

    public void setNombreGrado(String nombreGrado) {
        this.nombreGrado = nombreGrado;
    }

    public void setGradoColegio(String gradoColegio) {
        this.gradoColegio = gradoColegio;
    }

    public void setIdCarrera(String idCarrera) {
        this.idCarrera = idCarrera;
    }

    public void setIdGrado(String idGrado) {
        this.idGrado = idGrado;
    }
}
