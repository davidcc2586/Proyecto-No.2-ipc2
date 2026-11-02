package clasesAuxiliares;

import objetos.enums.RolUsuario;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

public final class SessionTokenService {
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final long TOKEN_LIFETIME_SECONDS = 8 * 60 * 60;
    private static final byte[] signingKey = loadSigningKey();

    private SessionTokenService() {
    }

    public static IssuedToken issue(int userId, RolUsuario role) {
        long expiresAt = Instant.now().getEpochSecond() + TOKEN_LIFETIME_SECONDS;
        String payload = userId + "|" + role.name() + "|" + expiresAt;
        String encodedPayload = Base64.getUrlEncoder().withoutPadding()
            .encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        return new IssuedToken(encodedPayload + "." + sign(encodedPayload), expiresAt);
    }

    public static Claims verify(String token) {
        if (token == null) {
            return null;
        }
        String[] parts = token.split("\\.", -1);
        if (parts.length != 2 || !MessageDigest.isEqual(
            parts[1].getBytes(StandardCharsets.US_ASCII),
            sign(parts[0]).getBytes(StandardCharsets.US_ASCII)
        )) {
            return null;
        }

        try {
            String payload = new String(Base64.getUrlDecoder().decode(parts[0]), StandardCharsets.UTF_8);
            String[] values = payload.split("\\|", -1);
            if (values.length != 3) {
                return null;
            }
            int userId = Integer.parseInt(values[0]);
            RolUsuario role = RolUsuario.valueOf(values[1]);
            long expiresAt = Long.parseLong(values[2]);
            if (userId <= 0 || expiresAt <= Instant.now().getEpochSecond()) {
                return null;
            }
            return new Claims(userId, role, expiresAt);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static String sign(String payload) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(new SecretKeySpec(signingKey, HMAC_ALGORITHM));
            return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(mac.doFinal(payload.getBytes(StandardCharsets.US_ASCII)));
        } catch (java.security.GeneralSecurityException e) {
            throw new IllegalStateException("No se pudo firmar el token de sesión.", e);
        }
    }

    private static byte[] createSigningKey() {
        byte[] key = new byte[32];
        new SecureRandom().nextBytes(key);
        return key;
    }

    private static byte[] loadSigningKey() {
        String configuredKey = System.getenv("SESSION_TOKEN_SECRET");
        if (configuredKey == null || configuredKey.isBlank()) {
            return createSigningKey();
        }
        byte[] key = configuredKey.getBytes(StandardCharsets.UTF_8);
        if (key.length < 32) {
            throw new ExceptionInInitializerError("SESSION_TOKEN_SECRET debe contener al menos 32 bytes.");
        }
        return key;
    }

    public record Claims(int userId, RolUsuario role, long expiresAt) {
    }

    public record IssuedToken(String value, long expiresAt) {
    }
}
