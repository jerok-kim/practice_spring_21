package kim.jerok.practice_spring_21.core.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import kim.jerok.practice_spring_21.model.user.User;

import java.util.Date;

public class JwtProvider {

    private static final String SUBJECT = "jwtstudy";
    private static final int EXP = 1000 * 60 * 60;
    public static final String TOKEN_PREFIX = "Bearer ";  // 스페이스 필요함
    public static final String HEADER = "Authorization";
    private static final String SECRET = "Rustacean";

    // 로그인 완료시
    public static String create(User user) {
        String jwt = JWT.create()
                .withSubject(SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXP))
                .withClaim("id", user.getId())
                .sign(Algorithm.HMAC512(SECRET));
        System.out.println("디버그 : 토큰 생성됨");
        return TOKEN_PREFIX + jwt;
    }

    // 인증이 필요한 리소스 접근시
    public static DecodedJWT verify(String jwt) throws SignatureVerificationException, TokenExpiredException {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET))
                .build().verify(jwt);
        System.out.println("디버그 : 토큰 검증됨");
        return decodedJWT;
    }

}
