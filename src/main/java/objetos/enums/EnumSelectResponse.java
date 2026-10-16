package objetos.enums;

public class EnumSelectResponse {
    private String valor;
    private String etiqueta;

    public EnumSelectResponse(String valor, String etiqueta) {
        this.valor = valor;
        this.etiqueta = etiqueta;
    }

    public String getValor() {
        return valor;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
