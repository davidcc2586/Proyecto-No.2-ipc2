package clasesAuxiliares;

import objetos.enums.RolUsuario;
import objetos.enums.TipoPago;
import objetos.exceptions.ValidacionException;
import objetos.objetosDB.*;
import objetos.objetosRequest.PagoRequest;
import objetos.objetosResponse.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PagoServicio {

    public int registrarPago(PagoRequest pago) throws SQLException,ValidacionException {
        if (TipoPago.valueOf(pago.getTipoPago()) == TipoPago.COLEGIATURA) {
            LocalDate fechaPago = LocalDate.parse(pago.getFecha());
            int idEstudiante = Integer.parseInt(pago.getIdEstudiante());
            PagoDB pagoDB = new PagoDB();
            int ultimoMesPagado = pagoDB.ultimoMesPagado(idEstudiante, fechaPago.getYear());
            int mesSiguiente = ultimoMesPagado + 1;
            if (mesSiguiente > 12) {
                throw new ValidacionException("El estudiante ya tiene registradas las 12 colegiaturas del año " + fechaPago.getYear() + ".");
            }
            if (fechaPago.getMonthValue() != mesSiguiente) {
                throw new ValidacionException("El mes que corresponde pagar es " + mesSiguiente + " (" + nombreMes(mesSiguiente) + ").");
            }
        }

        PagoDB pagoDB = new PagoDB();
        return pagoDB.ingresarNuevoPago(pago);
    }

    private String nombreMes(int mes) {
        return switch (mes) {
            case 1 -> "Enero";
            case 2 -> "Febrero";
            case 3 -> "Marzo";
            case 4 -> "Abril";
            case 5 -> "Mayo";
            case 6 -> "Junio";
            case 7 -> "Julio";
            case 8 -> "Agosto";
            case 9 -> "Septiembre";
            case 10 -> "Octubre";
            case 11 -> "Noviembre";
            case 12 -> "Diciembre";
            default -> String.valueOf(mes);
        };
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

    public EstadoCuentaResponse estadoCuenta(int idEstudiante) throws SQLException {
        PagoDB pagoDB = new PagoDB();
        return pagoDB.estadoCuenta(idEstudiante);
    }

    public List<UsuarioSelectResponse> selectSecretarios() throws SQLException{
        UsuarioDB usuarioDB = new UsuarioDB();
        List<UsuarioSelectResponse> secretariosSelect = new ArrayList<>();
        List<Usuario> secretarios = usuarioDB.usuariosSegunRol(RolUsuario.SECRETARIO);
        for (Usuario usuario : secretarios){
            secretariosSelect.add(new UsuarioSelectResponse(usuario.getIdUsuario(), usuario.getNombre() + " " + usuario.getApellido()));
        }
        return secretariosSelect;
    }
}
