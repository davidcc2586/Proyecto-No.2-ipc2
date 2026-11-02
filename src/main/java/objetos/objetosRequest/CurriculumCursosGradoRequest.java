package objetos.objetosRequest;

public class CurriculumCursosGradoRequest {
    private String idGrado;
    private String idCurso;

    public CurriculumCursosGradoRequest(){

    }

    public CurriculumCursosGradoRequest(String idGrado, String idCurso) {
        this.idGrado = idGrado;
        this.idCurso = idCurso;
    }

    public String getIdGrado() {
        return idGrado;
    }

    public String getIdCurso() {
        return idCurso;
    }

    public void setIdGrado(String idGrado) {
        this.idGrado = idGrado;
    }

    public void setIdCurso(String idCurso) {
        this.idCurso = idCurso;
    }
}
