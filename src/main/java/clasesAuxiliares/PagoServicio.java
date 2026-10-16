package clasesAuxiliares;

import objetos.exceptions.ValidacionException;
import objetos.objetosDB.*;
import objetos.objetosRequest.PagoRequest;
import objetos.objetosResponse.Estudiante;
import objetos.objetosResponse.Grado;
import objetos.objetosResponse.Solvencia;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class PagoServicio {

    public void registrarPago(PagoRequest pago) throws SQLException,ValidacionException {
        try {
            double cantidad = Double.parseDouble(pago.getCantidad());
            if (cantidad < 0){
                throw new ValidacionException("Cantidad de pago invalida");
            }
        } catch (NumberFormatException e) {
            throw new ValidacionException("Cantidad de pago invalida");
        }
        try {
            LocalDate fechaPago = LocalDate.parse(pago.getFecha());
            ConfiguracionColegioDB configuracionColegioDB = new ConfiguracionColegioDB();
            int anioLectivo = configuracionColegioDB.obtenerAñoLectivoActual();
            if (anioLectivo != fechaPago.getYear()){
                throw new ValidacionException("Fecha de pago invalida, el año lectivo actual es: " + anioLectivo);
            }

            PagoDB pagoDB = new PagoDB();
            int mes = fechaPago.getMonthValue();
            int ultimoMesPagado = pagoDB.ultimoMesPagado(Integer.parseInt(pago.getIdEstudiante()),anioLectivo);
            if(ultimoMesPagado == -1){
                if (mes != 1){
                    throw new ValidacionException("Fecha de pago invalida, el mes que corresponde pagar es el No. 1");
                }
            }else if (mes != ultimoMesPagado + 1){
                throw new ValidacionException("Fecha de pago invalida, el mes que corresponde pagar es el No. " + ultimoMesPagado + 1);
            }
        } catch (NumberFormatException e) {
            throw new ValidacionException("Fecha de pago invalida");
        }

        PagoDB pagoDB = new PagoDB();
        pagoDB.ingresarNuevoPago(pago);
    }

    public Solvencia emisionSolvenciaEconomica(int idEstudiante,int anioSolvencia, int mes) throws SQLException{
        EstudianteDB estudianteDB = new EstudianteDB();
        Optional<Estudiante> estudiante = estudianteDB.obtenerEstudiante(idEstudiante);
        if (estudiante.isEmpty()){
            throw new SQLException("Usuario no encontrado.");
        }
        PagoDB pagoDB = new PagoDB();
        String estadoSolvencia;
        int cantidadPagosRealizados = pagoDB.cantidadPagosRealizados(idEstudiante,anioSolvencia);
        GradoDB gradoDB = new GradoDB();
        Grado grado = gradoDB.gradoEstudiante(idEstudiante,anioSolvencia) ;
        if (mes <= cantidadPagosRealizados){
            estadoSolvencia = "Solvente";
        }else {
            estadoSolvencia = "Insolvente";
        }
        return new Solvencia(estudiante.get().getNombre() + " " + estudiante.get().getApellido(),estudiante.get().getCui(), grado.getNombreGrado(),anioSolvencia,estadoSolvencia, mes);
    }
}
