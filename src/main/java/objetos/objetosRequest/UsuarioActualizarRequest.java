package objetos.objetosRequest;

public class UsuarioActualizarRequest {
    private String idUsuario;
    private String clave;
    private String claveConfirmacion;
    private String usuario;
    private String salario;
    private String rolUsuario;

    public UsuarioActualizarRequest(String idUsuario, String clave, String claveConfirmacion, String usuario, String salario, String rolUsuario) {
        this.idUsuario = idUsuario;
        this.clave = clave;
        this.claveConfirmacion = claveConfirmacion;
        this.usuario = usuario;
        this.salario = salario;
        this.rolUsuario = rolUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getClave() {
        return clave;
    }

    public String getClaveConfirmacion() {
        return claveConfirmacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSalario() {
        return salario;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setClaveConfirmacion(String claveConfirmacion) {
        this.claveConfirmacion = claveConfirmacion;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }
}
