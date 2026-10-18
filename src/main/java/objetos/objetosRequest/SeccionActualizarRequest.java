package objetos.objetosRequest;

public class SeccionActualizarRequest {
    private String idGrado;
    private String anio;
    private String nombreSeccion;
    private String idSeccion;

    public SeccionActualizarRequest(String idSeccion, String idGrado, String anio, String nombreSeccion) {
        this.idSeccion = idSeccion;
        this.idGrado = idGrado;
        this.anio = anio;
        this.nombreSeccion = nombreSeccion;
    }

    public String getIdSeccion() {
        return idSeccion;
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

    public void setIdSeccion(String idSeccion) {
        this.idSeccion = idSeccion;
    }
}
