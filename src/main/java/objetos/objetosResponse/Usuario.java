package objetos.objetosResponse;

import objetos.enums.RolUsuario;

import java.time.LocalDate;

public class Usuario {
    private int idUsuario;
    private String cui;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String usuario;
    private String clave;
    private RolUsuario rolUsuario;
    private LocalDate fechaContratacion;
    private double salario;

    public Usuario(int idUsuario, String cui, String nombre, String apellido, LocalDate fechaNacimiento, String usuario, String clave, RolUsuario rolUsuario, LocalDate fechaContratacion, double salario) {
        this.idUsuario = idUsuario;
        this.cui = cui;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.usuario = usuario;
        this.clave = clave;
        this.rolUsuario = rolUsuario;
        this.fechaContratacion = fechaContratacion;
        this.salario = salario;
    }

    public int getIdUsuario() {
        return idUsuario;
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

    public RolUsuario getRolUsuario() {
        return rolUsuario;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public double getSalario() {
        return salario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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

    public void setRolUsuario(RolUsuario rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
}
