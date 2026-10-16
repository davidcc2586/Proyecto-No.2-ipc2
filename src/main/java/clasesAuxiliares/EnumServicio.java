package clasesAuxiliares;

import objetos.enums.*;

import java.util.ArrayList;
import java.util.List;

public class EnumServicio {
    public List<EnumSelectResponse> clasificarTipoEnum(String tipoEnum){
        List<EnumSelectResponse> enums = new ArrayList<>();
        if (tipoEnum.equalsIgnoreCase("Estado")){
            for (Estado estado : Estado.values()){
                enums.add(new EnumSelectResponse(estado.name(), estado.getdescripcion()));
            }
        }
        if (tipoEnum.equalsIgnoreCase("EstadoCarrera")){
            for (EstadoCarrera estadoCarrera : EstadoCarrera.values()){
                enums.add(new EnumSelectResponse(estadoCarrera.name(), estadoCarrera.getDescripcion()));
            }
        }
        if (tipoEnum.equalsIgnoreCase("EstadoEstudiante")){
            for (EstadoEstudiante estadoEstudiante : EstadoEstudiante.values()){
                enums.add(new EnumSelectResponse(estadoEstudiante.name(), estadoEstudiante.getDescripcion()));
            }
        }
        if (tipoEnum.equalsIgnoreCase("EstadoFinalPrestamoLibro")){
            for (EstadoFinalPrestamoLibro estadoFinalPrestamoLibro : EstadoFinalPrestamoLibro.values()){
                enums.add(new EnumSelectResponse(estadoFinalPrestamoLibro.name(), estadoFinalPrestamoLibro.getDescripcion()));
            }
        }
        if (tipoEnum.equalsIgnoreCase("EstadoPrestamoLibro")){
            for (EstadoPrestamoLibro estadoPrestamoLibro : EstadoPrestamoLibro.values()){
                enums.add(new EnumSelectResponse(estadoPrestamoLibro.name(), estadoPrestamoLibro.getDescripcion()));
            }
        }
        if (tipoEnum.equalsIgnoreCase("GradoColegio")){
            for (GradoColegio gradoColegio : GradoColegio.values()){
                enums.add(new EnumSelectResponse(gradoColegio.name(), gradoColegio.getDescripcion()));
            }
        }
        if (tipoEnum.equalsIgnoreCase("RolUsuario")){
            for (RolUsuario rolUsuario : RolUsuario.values()){
                enums.add(new EnumSelectResponse(rolUsuario.name(), rolUsuario.getDescripcion()));
            }
        }
        if (tipoEnum.equalsIgnoreCase("TipoActividad")){
            for (TipoActividad tipoActividad : TipoActividad.values()){
                enums.add(new EnumSelectResponse(tipoActividad.name(), tipoActividad.getDescripcion()));
            }
        }
        if (tipoEnum.equalsIgnoreCase("TipoPago")){
            for (TipoPago tipoPago : TipoPago.values()){
                enums.add(new EnumSelectResponse(tipoPago.name(), tipoPago.getDescripcion()));
            }
        }
        if (tipoEnum.equalsIgnoreCase("Mes")){
            for (Mes mes : Mes.values()){
                enums.add(new EnumSelectResponse(mes.getdescripcion(),mes.name()));
            }
        }
        return enums;
    }
}
