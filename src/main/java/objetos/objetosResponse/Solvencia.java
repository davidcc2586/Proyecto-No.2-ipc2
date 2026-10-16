package objetos.objetosResponse;

public class Solvencia {
    private final String encabezado = "COLEGIO PRESTIGIOSO - DEPARTAMENTO DE FINANZAS" ;
    private final String titulo = "CONSTANCIA DE PAZ Y SALVO (SOLVENCIA)";
    private final String firmas = "Sello y Firma - Administración";
    private String nombreEstudiante;
    private String cuiEstudiante;
    private String gradoSeccion;
    private int anioLectivo;
    private String estadoSolvencia;
    private int mes;

    public Solvencia(String nombreEstudiante, String cuiEstudiante, String gradoSeccion, int anioLectivo, String estadoSolvencia, int mes) {
        this.nombreEstudiante = nombreEstudiante;
        this.cuiEstudiante = cuiEstudiante;
        this.gradoSeccion = gradoSeccion;
        this.anioLectivo = anioLectivo;
        this.estadoSolvencia = estadoSolvencia;
        this.mes = mes;
    }

    public String getEncabezado() {
        return encabezado;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getFirmas() {
        return firmas;
    }

    public String getContenido() {
        return "A quien corresponda:\n\n" +
                "El Colegio Prestigioso hace constar que el/la estudiante " + nombreEstudiante +
                ", con número de CUI " + cuiEstudiante +
                ", se encuentra debidamente inscrito(a) en el grado de " + gradoSeccion +
                ". Tras la revisión del registro financiero correspondiente al ciclo " + anioLectivo +
                ", se extiende la presente constancia indicando que su estado actual es: " + estadoSolvencia.toUpperCase() +
                " (evaluado al mes número " + mes + ").";
    }
}