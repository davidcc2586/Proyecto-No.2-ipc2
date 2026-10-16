package objetos.objetosResponse;

public class EstudianteSelectResponse {
    private int idEstudiante;
    private String nombreCompleto;

    public EstudianteSelectResponse(int idEstudiante, String nombreCompleto) {
        this.idEstudiante = idEstudiante;
        this.nombreCompleto = nombreCompleto;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
}
