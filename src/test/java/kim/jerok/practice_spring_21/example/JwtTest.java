package kim.jerok.practice_spring_21.example;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

// JSON Web Token
public class JwtTest {

    @Test
    public void createJwt_test() {
        // given

        // when
        String jwt = JWT.create()
                .withSubject("토큰제목")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .withClaim("id", 1)
                .withClaim("role", "guest")
                .sign(Algorithm.HMAC512("Jerok"));
        System.out.println(jwt);

        // then
    }

    @Test
    @DisplayName("verifyJwt test")
    public void verifyJwt_test() throws Exception {
        // given
        // String jwt = JWT.create()
        //         .withSubject("토큰 제목")
        //         .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
        //         .withClaim("id", 1)
        //         .withClaim("role", "guest")
        //         .sign(Algorithm.HMAC512("jerok"));
        // System.out.println(jwt);
        
        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiLthqDtgbDsoJzrqqkiLCJyb2xlIjoiZ3Vlc3QiLCJpZCI6MSwiZXhwIjoxNjgzMjI2MTQ0fQ.lOPaPPzWkNy0Mtrr7rQnn3ZAeUJA6445peFledXqQMR1msa6gljGNLsD0XVezkoa1S78b0iSXRGeDXh7WyukFA";

        // when
        try {
            // BASE64 디코딩 -> 토큰 검증 (내가 만든 토큰이 맞다)
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("jerk"))
                    .build().verify(jwt);
            int id = decodedJWT.getClaim("id").asInt();
            String role = decodedJWT.getClaim("role").asString();
            System.out.println(id);
            System.out.println(role);
        } catch (SignatureVerificationException sve) {
            System.out.println("토큰 검증 실패 " + sve.getMessage());  // 위조
        } catch (TokenExpiredException tee) {
            System.out.println("토큰 만료 " + tee.getMessage());  // 오래됨
        }

        // then
    }

}
