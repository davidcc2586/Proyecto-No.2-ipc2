package objetos.objetosRequest;

public class AsignacionEstudianteRequest {
    private String idSeccion;
    private String idEstudiante;
    public AsignacionEstudianteRequest(){
    }

    public AsignacionEstudianteRequest(String idSeccion, String isEstudiante) {
        this.idSeccion = idSeccion;
        this.idEstudiante = isEstudiante;
    }

    public String getIdSeccion() {
        return idSeccion;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdSeccion(String idSeccion) {
        this.idSeccion = idSeccion;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
}
