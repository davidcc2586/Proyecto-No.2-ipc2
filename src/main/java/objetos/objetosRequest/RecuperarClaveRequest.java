package objetos.objetosRequest;

public class RecuperarClaveRequest {
    private String usuario;
    private String fechaNacimiento;
    private String cui;

    public RecuperarClaveRequest(){

    }
    public RecuperarClaveRequest(String usuario, String fechaNacimiento, String cui) {
        this.usuario = usuario;
        this.fechaNacimiento = fechaNacimiento;
        this.cui = cui;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getCui() {
        return cui;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }
}
