package objetos;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class UtilidadesBase64 {

    public UtilidadesBase64(){
    }

    public String codificar(String textoOriginal) {
        return Base64.getEncoder().encodeToString(textoOriginal.getBytes(StandardCharsets.UTF_8));
    }

    public String decodificar(String textoCodificado) {
        byte[] bytesDecodificados = Base64.getDecoder().decode(textoCodificado);
        return new String(bytesDecodificados, StandardCharsets.UTF_8);
    }

}