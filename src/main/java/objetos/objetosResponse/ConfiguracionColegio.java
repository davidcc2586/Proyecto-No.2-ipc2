package objetos.objetosResponse;

import objetos.enums.Estado;

public class ConfiguracionColegio {
    private int anioLectivo;
    private Estado estado;

    public ConfiguracionColegio(int anioLectivo, Estado estado) {
        this.anioLectivo = anioLectivo;
        this.estado = estado;
    }

    public int getAnioLectivo() {
        return anioLectivo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setAnioLectivo(int anioLectivo) {
        this.anioLectivo = anioLectivo;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
