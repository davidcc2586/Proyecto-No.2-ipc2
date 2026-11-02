package objetos.objetosRequest;

public class SeccionRequest {
    private String idGrado;
    private String anio;
    private String nombreSeccion;

    public SeccionRequest(){

    }
    public SeccionRequest(String idGrado, String anio, String nombreSeccion) {
        this.idGrado = idGrado;
        this.anio = anio;
        this.nombreSeccion = nombreSeccion;
    }

    public String getIdGrado() {
        return idGrado;
    }

    public String getAnio() {
        return anio;
    }

    public String getNombreSeccion() {
        return nombreSeccion;
    }

    public void setIdGrado(String idGrado) {
        this.idGrado = idGrado;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public void setNombreSeccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }
}
