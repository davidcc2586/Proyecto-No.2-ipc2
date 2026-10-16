package objetos.objetosResponse;

import objetos.enums.EstadoEstudiante;

import java.time.LocalDate;

public class Estudiante {
    private int idEstudiante;
    private String cui;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String usuario;
    private String clave;
    private int anioInscripcion;
    private EstadoEstudiante estadoEstudiante;
    private String tipoSangre;
    private String alergia;
    private String nombreEncargado;
    private String telefonoEncargado;
    private String correoEncargado;

    public Estudiante(int idEstudiante, String cui, String nombre, String apellido, LocalDate fechaNacimiento, String usuario, String clave, int anioInscripcion, EstadoEstudiante estadoEstudiante, String tipoSangre, String alergia, String nombreEncargado, String telefonoEncargado, String correoEncargado) {
        this.idEstudiante = idEstudiante;
        this.cui = cui;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.usuario = usuario;
        this.clave = clave;
        this.anioInscripcion = anioInscripcion;
        this.estadoEstudiante = estadoEstudiante;
        this.tipoSangre = tipoSangre;
        this.alergia = alergia;
        this.nombreEncargado = nombreEncargado;
        this.telefonoEncargado = telefonoEncargado;
        this.correoEncargado = correoEncargado;
    }

    public int getIdEstudiante() {
        return idEstudiante;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getClave() {
        return clave;
    }

    public int getAnioInscripcion() {
        return anioInscripcion;
    }

    public EstadoEstudiante getEstadoEstudiante() {
        return estadoEstudiante;
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

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
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

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setAnioInscripcion(int anioInscripcion) {
        this.anioInscripcion = anioInscripcion;
    }

    public void setEstadoEstudiante(EstadoEstudiante estadoEstudiante) {
        this.estadoEstudiante = estadoEstudiante;
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
