package objetos.objetosRequest;

public class EstudianteActualizarRequest {
    private int idEstudiante;
    private String alergia;
    private String nombreEncargado;
    private String telefonoEncargado;
    private String correoEncargado;

    public EstudianteActualizarRequest(){

    }

    public EstudianteActualizarRequest(int idEstudiante, String alergia, String nombreEncargado, String telefonoEncargado, String correoEncargado) {
        this.idEstudiante = idEstudiante;
        this.alergia = alergia;
        this.nombreEncargado = nombreEncargado;
        this.telefonoEncargado = telefonoEncargado;
        this.correoEncargado = correoEncargado;
    }

    public String getAlergia() {
        return alergia;
    }

    public String getNombreEncargado() {
        return nombreEncargado;
    }

    public String getTelefonoEncargado() {
        return telefonoEncargado;
    }

    public String getCorreoEncargado() {
        return correoEncargado;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setAlergia(String alergia) {
        this.alergia = alergia;
    }

    public void setNombreEncargado(String nombreEncargado) {
        this.nombreEncargado = nombreEncargado;
    }

    public void setTelefonoEncargado(String telefonoEncargado) {
        this.telefonoEncargado = telefonoEncargado;
    }

    public void setCorreoEncargado(String correoEncargado) {
        this.correoEncargado = correoEncargado;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
}
