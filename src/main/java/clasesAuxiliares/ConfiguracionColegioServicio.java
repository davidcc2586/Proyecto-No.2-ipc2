package clasesAuxiliares;

import objetos.objetosDB.ConfiguracionColegioDB;
import objetos.objetosResponse.ConfiguracionColegio;

import java.sql.SQLException;
import java.util.List;

public class ConfiguracionColegioServicio {
    public void nuevoAñoLectivo(int anio) throws SQLException {
        ConfiguracionColegioDB configuracionColegioDB = new ConfiguracionColegioDB();
        configuracionColegioDB.nuevoAñoLectivo(anio);
    }

    public List<ConfiguracionColegio> obtenerAñosLectivos() throws SQLException {
        ConfiguracionColegioDB configuracionColegioDB = new ConfiguracionColegioDB();
        return configuracionColegioDB.obtenerAñosLectivos();
    }

    public void cambiaraAniLectivo(int anio) throws SQLException{
        ConfiguracionColegioDB configuracionColegioDB = new ConfiguracionColegioDB();
        configuracionColegioDB.cambiaraAniLectivo(anio);
    }
}
