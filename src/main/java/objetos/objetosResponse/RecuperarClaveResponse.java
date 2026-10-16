package objetos.objetosResponse;

public class RecuperarClaveResponse {
    private String clave;

    public RecuperarClaveResponse() {}

    public RecuperarClaveResponse(String clave) {
        this.clave = clave;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}