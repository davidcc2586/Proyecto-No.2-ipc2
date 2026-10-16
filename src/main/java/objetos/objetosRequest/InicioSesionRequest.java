package objetos.objetosRequest;

public class InicioSesionRequest {
    private String usuario;
    private String clave;

    public InicioSesionRequest(String usuario, String contraseña) {
        this.usuario = usuario;
        this.clave = contraseña;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getClave() {
        return clave;
    }


}
