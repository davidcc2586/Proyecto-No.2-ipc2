package objetos.objetosResponse;

import objetos.enums.RolUsuario;

public class InicioSesion {
    private int idUsuario;
    private String usuario;
    private RolUsuario rolUsuario;
    private int anioLectivo;

    public InicioSesion(int idUsuario, String usuario, RolUsuario rolUsuario, int anioLectivo) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.rolUsuario = rolUsuario;
        this.anioLectivo = anioLectivo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public RolUsuario getRolUsuario() {
        return rolUsuario;
    }

    public int getAnioLectivo() {
        return anioLectivo;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setRolUsuario(RolUsuario rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public void setAnioLectivo(int anioLectivo) {
        this.anioLectivo = anioLectivo;
    }
}
