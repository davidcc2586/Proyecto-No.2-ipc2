package clasesAuxiliares;

import objetos.objetosDB.ConfiguracionColegioDB;
import objetos.objetosResponse.ConfiguracionColegio;

import java.sql.SQLException;
import java.util.List;

public class ConfiguracionColegioServicio {
    public void cambiarAnioLectivo(int anio) throws SQLException {
        ConfiguracionColegioDB configuracionColegioDB = new ConfiguracionColegioDB();
        configuracionColegioDB.cambiarAñoLectivo(anio);
    }

    public List<ConfiguracionColegio> obtenerAñosLectivos() throws SQLException {
        ConfiguracionColegioDB configuracionColegioDB = new ConfiguracionColegioDB();
        return configuracionColegioDB.obtenerAñosLectivos();
    }
}
