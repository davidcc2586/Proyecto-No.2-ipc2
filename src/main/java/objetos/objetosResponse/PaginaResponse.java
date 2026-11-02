package objetos.objetosResponse;

import java.util.List;

public class PaginaResponse<T> {
    private List<T> contenido;
    private int pagina;
    private int tamano;
    private int totalElementos;
    private int totalPaginas;

    public PaginaResponse() {
    }

    public PaginaResponse(List<T> contenido, int pagina, int tamano, int totalElementos) {
        this.contenido = contenido;
        this.pagina = pagina;
        this.tamano = tamano;
        this.totalElementos = totalElementos;
        this.totalPaginas = 0;
        if (totalElementos > 0) {
            this.totalPaginas = totalElementos / tamano;
            if (totalElementos % tamano > 0) {
                this.totalPaginas++;
            }
        }
    }

    public List<T> getContenido() {
        return contenido;
    }

    public int getPagina() {
        return pagina;
    }

    public int getTamano() {
        return tamano;
    }

    public int getTotalElementos() {
        return totalElementos;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setContenido(List<T> contenido) {
        this.contenido = contenido;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    public void setTotalElementos(int totalElementos) {
        this.totalElementos = totalElementos;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }
}
