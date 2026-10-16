package objetos.objetosRequest;

public class EstudianteRequest {
    private String cui;
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private String usuario;
    private String clave;
    private String claveConfirmacion;
    private String anioInscripcion;
    private String tipoSangre;
    private String alergia;
    private String nombreEncargado;
    private String telefonoEncargado;
    private String correoEncargado;

    public EstudianteRequest(String cui, String nombre, String apellido, String fechaNacimiento, String usuario, String clave, String claveConfirmacion, String anioInscripcion, String tipoSangre, String alergia, String nombreEncargado, String telefonoEncargado, String correoEncargado) {
        this.cui = cui;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.usuario = usuario;
        this.clave = clave;
        this.claveConfirmacion = claveConfirmacion;
        this.anioInscripcion = anioInscripcion;
        this.tipoSangre = tipoSangre;
        this.alergia = alergia;
        this.nombreEncargado = nombreEncargado;
        this.telefonoEncargado = telefonoEncargado;
        this.correoEncargado = correoEncargado;
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

    public String getAnioInscripcion() {
        return anioInscripcion;
    }

    public String getTipoSangre() {
        return tipoSangre;
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

    public void setAnioInscripcion(String anioInscripcion) {
        this.anioInscripcion = anioInscripcion;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
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
}
