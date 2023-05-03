package kim.jerok.practice_spring_21.example;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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
                .withExpiresAt(new Date(System.currentTimeMillis() + 100 * 60 * 60 * 24 * 7))
                .withClaim("id", 1)
                .withClaim("role", "guest")
                .sign(Algorithm.HMAC512("Jerok"));
        System.out.println(jwt);

        // then
    }

}
