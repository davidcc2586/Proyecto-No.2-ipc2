package objetos.objetosResponse;

public class LibroSelectResponse {
    public int idLibro;
    public String titulo;

    public LibroSelectResponse(){

    }
    public LibroSelectResponse(int idLibro, String titulo){
        this.idLibro = idLibro;
        this.titulo = titulo;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
