package objetos.objetosResponse;

import objetos.enums.RolUsuario;

public class SesionAutenticadaResponse {
    private int idUsuario;
    private String usuario;
    private RolUsuario rolUsuario;
    private int anioLectivo;
    private String accessToken;
    private long expiresAt;

    public SesionAutenticadaResponse() {
    }

    public SesionAutenticadaResponse(InicioSesion sesion, String accessToken, long expiresAt) {
        this.idUsuario = sesion.getIdUsuario();
        this.usuario = sesion.getUsuario();
        this.rolUsuario = sesion.getRolUsuario();
        this.anioLectivo = sesion.getAnioLectivo();
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
    }

    public int getIdUsuario() { return idUsuario; }
    public String getUsuario() { return usuario; }
    public RolUsuario getRolUsuario() { return rolUsuario; }
    public int getAnioLectivo() { return anioLectivo; }
    public String getAccessToken() { return accessToken; }
    public long getExpiresAt() { return expiresAt; }

    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public void setRolUsuario(RolUsuario rolUsuario) { this.rolUsuario = rolUsuario; }
    public void setAnioLectivo(int anioLectivo) { this.anioLectivo = anioLectivo; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public void setExpiresAt(long expiresAt) { this.expiresAt = expiresAt; }
}
