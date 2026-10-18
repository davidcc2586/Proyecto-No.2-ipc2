package objetos.objetosRequest;

public class NotaRequest {
    private String idActividad;
    private String idEstudiante;
    private String punteoObtenido;

    public NotaRequest(String idActividad, String idEstudiante, String punteoObtenido) {
        this.idActividad = idActividad;
        this.idEstudiante = idEstudiante;
        this.punteoObtenido = punteoObtenido;
    }

    public String getIdActividad() {
        return idActividad;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public String getPunteoObtenido() {
        return punteoObtenido;
    }

    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public void setPunteoObtenido(String punteoObtenido) {
        this.punteoObtenido = punteoObtenido;
    }
}
