package objetos.objetosRequest;

public class UsuarioRequest {
    private String cui;
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private String usuario;
    private String clave;
    private String claveConfirmacion;
    private String rolUsuario;
    private String fechaContratacion;
    private String salario;

    public UsuarioRequest() {
    }

    public UsuarioRequest(String cui, String nombre, String apellido, String fechaNacimiento, String usuario, String clave, String claveConfirmacion, String rolUsuario, String fechaContratacion, String salario) {
        this.cui = cui;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.usuario = usuario;
        this.clave = clave;
        this.claveConfirmacion = claveConfirmacion;
        this.rolUsuario = rolUsuario;
        this.fechaContratacion = fechaContratacion;
        this.salario = salario;
    }

    public String getCui() {
        return cui;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getClave() {
        return clave;
    }

    public String getClaveConfirmacion() {
        return claveConfirmacion;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public String getFechaContratacion() {
        return fechaContratacion;
    }

    public String getSalario() {
        return salario;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setClaveConfirmacion(String claveConfirmacion) {
        this.claveConfirmacion = claveConfirmacion;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public void setFechaContratacion(String fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }
}
