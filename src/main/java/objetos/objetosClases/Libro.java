package objetos.objetosClases;

public class Libro {
    private int idLibro;
    private int autor;
    private int anio;
    private String titulo;
    private String editorial;
    private int cantidad;

    public Libro(int idLibro, int autor, int anio, String titulo, String editorial, int cantidad) {
        this.idLibro = idLibro;
        this.autor = autor;
        this.anio = anio;
        this.titulo = titulo;
        this.editorial = editorial;
        this.cantidad = cantidad;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public int getAutor() {
        return autor;
    }

    public int getAnio() {
        return anio;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getEditorial() {
        return editorial;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public void setAutor(int autor) {
        this.autor = autor;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
