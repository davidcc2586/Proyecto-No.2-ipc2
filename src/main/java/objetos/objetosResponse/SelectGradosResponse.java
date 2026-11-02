package objetos.objetosResponse;

public class SelectGradosResponse {
    private int idGrado;
    private String nombreGrado;

    public SelectGradosResponse(){

    }

    public SelectGradosResponse(int idGrado, String nombreGrado) {
        this.idGrado = idGrado;
        this.nombreGrado = nombreGrado;
    }

    public int getIdGrado() {
        return idGrado;
    }

    public String getNombreGrado() {
        return nombreGrado;
    }

    public void setIdGrado(int idGrado) {
        this.idGrado = idGrado;
    }

    public void setNombreGrado(String nombreGrado) {
        this.nombreGrado = nombreGrado;
    }
}
