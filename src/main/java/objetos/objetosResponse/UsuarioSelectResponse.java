package objetos.objetosResponse;

public class UsuarioSelectResponse {
    private int idUsuario;
    private String nombreCompleto;

    public UsuarioSelectResponse(){

    }
    public UsuarioSelectResponse(int idUsuario, String nombreCompleto) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
}
