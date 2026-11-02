package objetos.objetosResponse;

import objetos.enums.EstadoEstudiante;

import java.time.LocalDate;

public class EstudianteDatosResponse {
    private String cui;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String tipoSangre;
    private String alergia;
    private String nombreEncargado;
    private String telefonoEncargado;
    private String correoEncargado;
    private int anioInscripcion;
    private EstadoEstudiante estadoEstudiante;

    public EstudianteDatosResponse(){

    }

    public EstudianteDatosResponse(String cui, String nombre, String apellido, LocalDate fechaNacimiento, String tipoSangre, String alergia, String nombreEncargado, String telefonoEncargado, String correoEncargado, int anioInscripcion, EstadoEstudiante estadoEstudiante) {
        this.cui = cui;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoSangre = tipoSangre;
        this.alergia = alergia;
        this.nombreEncargado = nombreEncargado;
        this.telefonoEncargado = telefonoEncargado;
        this.correoEncargado = correoEncargado;
        this.anioInscripcion = anioInscripcion;
        this.estadoEstudiante = estadoEstudiante;
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

    public int getAnioInscripcion() {
        return anioInscripcion;
    }

    public EstadoEstudiante getEstadoEstudiante() {
        return estadoEstudiante;
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

    public void setAnioInscripcion(int anioInscripcion) {
        this.anioInscripcion = anioInscripcion;
    }

    public void setEstadoEstudiante(EstadoEstudiante estadoEstudiante) {
        this.estadoEstudiante = estadoEstudiante;
    }
}
