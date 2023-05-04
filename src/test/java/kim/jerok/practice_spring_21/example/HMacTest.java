package kim.jerok.practice_spring_21.example;

import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HMacTest {
    
    @Test
    public void hmac_test() {
        String message = "Study Hard";
        String secretKey = "jerok";

        try {
            String hashedMessage = hmacSha512(message, secretKey);
            System.out.println("HMAC-SHA512 Hashed Message: " + hashedMessage);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    private static String hmacSha512(String message, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        mac.init(secretKeySpec);
        byte[] hmacSha512Bytes = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

        // 바이트 배열을 Base64로 인코딩하여 문자열로 변환
        return Base64.getEncoder().encodeToString(hmacSha512Bytes);
    }
}
