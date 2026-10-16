package objetos.objetosRequest;

public class LibroActualizarRequest {
    private String idLibro;
    private String autor;
    private String anio;
    private String titulo;
    private String editorial;

    public LibroActualizarRequest(String idLibro, String autor, String anio, String titulo, String editorial) {
        this.idLibro = idLibro;
        this.autor = autor;
        this.anio = anio;
        this.titulo = titulo;
        this.editorial = editorial;
    }

    public String getIdLibro() {
        return idLibro;
    }

    public String getAutor() {
        return autor;
    }

    public String getAnio() {
        return anio;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
}

