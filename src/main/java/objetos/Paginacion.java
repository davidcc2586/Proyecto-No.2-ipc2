package objetos;

public class Paginacion {
    public Paginacion() {
    }

    public int calcularDesplazamiento(int pagina, int tamano) {
        if (pagina < 0) {
            throw new IllegalArgumentException("La página no puede ser negativa.");
        }
        if (tamano < 1 || tamano > 100) {
            throw new IllegalArgumentException("El tamaño debe estar entre 1 y 100.");
        }
        if (pagina > Integer.MAX_VALUE / tamano) {
            throw new IllegalArgumentException("La página solicitada es demasiado grande.");
        }
        return pagina * tamano;
    }
}
