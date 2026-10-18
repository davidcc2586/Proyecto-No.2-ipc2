package objetos.objetosRequest;

public class AsignacionEstudianteRequest {
    private String idSeccion;
    private String isEstudiante;

    public AsignacionEstudianteRequest(String idSeccion, String isEstudiante) {
        this.idSeccion = idSeccion;
        this.isEstudiante = isEstudiante;
    }

    public String getIdSeccion() {
        return idSeccion;
    }

    public String getIsEstudiante() {
        return isEstudiante;
    }

    public void setIdSeccion(String idSeccion) {
        this.idSeccion = idSeccion;
    }

    public void setIsEstudiante(String isEstudiante) {
        this.isEstudiante = isEstudiante;
    }
}
