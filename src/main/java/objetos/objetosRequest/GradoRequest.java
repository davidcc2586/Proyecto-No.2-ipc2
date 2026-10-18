package objetos.objetosRequest;

public class GradoRequest {
    public String nombreGrado;
    private String gradoColegio;
    private String idCarrera;

    public GradoRequest(String nombreGrado, String gradoColegio, String idCarrera) {
        this.nombreGrado = nombreGrado;
        this.gradoColegio = gradoColegio;
        this.idCarrera = idCarrera;
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

    public void setNombreGrado(String nombreGrado) {
        this.nombreGrado = nombreGrado;
    }

    public void setGradoColegio(String gradoColegio) {
        this.gradoColegio = gradoColegio;
    }

    public void setIdCarrera(String idCarrera) {
        this.idCarrera = idCarrera;
    }
}
