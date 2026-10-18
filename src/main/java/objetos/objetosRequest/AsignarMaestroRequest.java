package objetos.objetosRequest;

public class AsignarMaestroRequest {

    private String idSeccion;
    private String idMaestro;
    private String idCurso;

    public AsignarMaestroRequest(String idSeccion, String idMaestro, String idCurso) {
        this.idSeccion = idSeccion;
        this.idMaestro = idMaestro;
        this.idCurso = idCurso;
    }

    public String getIdSeccion() {
        return idSeccion;
    }

    public String getIdMaestro() {
        return idMaestro;
    }

    public String getIdCurso() {
        return idCurso;
    }

    public void setIdSeccion(String idSeccion) {
        this.idSeccion = idSeccion;
    }

    public void setIdMaestro(String idMaestro) {
        this.idMaestro = idMaestro;
    }

    public void setIdCurso(String idCurso) {
        this.idCurso = idCurso;
    }
}
