package kim.jerok.practice_spring_21.core.filter;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import kim.jerok.practice_spring_21.core.exception.Exception400;
import kim.jerok.practice_spring_21.core.jwt.JwtProvider;
import kim.jerok.practice_spring_21.core.session.LoginUser;
import kim.jerok.practice_spring_21.dto.ResponseDto;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class JwtVerifyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("디버그 : JwtVerifyFilter 동작합");

        // 1. 다운캐스팅
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 2. 헤더 검증
        String prefixJwt = req.getHeader(JwtProvider.HEADER);
        if (prefixJwt == null) {
            error(resp, new Exception400("토큰이 전달되지 않았습니다"));
            return;
        }

        // 3. Bearer 제거
        String jwt = prefixJwt.replace(JwtProvider.TOKEN_PREFIX, "");

        try {
            // 4. 검증
            DecodedJWT decodedJWT = JwtProvider.verify(jwt);
            int id = decodedJWT.getClaim("id").asInt();
            String role = decodedJWT.getClaim("role").asString();

            // 5. 세션생성 - 세션값으로 권한처리하기 위해
            HttpSession session = req.getSession();
            LoginUser loginUser = LoginUser.builder().id(id).role(role).build();
            session.setAttribute("loginUser", loginUser);
            
            // 6. 다음 필터로 가! -> DS로!
            chain.doFilter(req, resp);
        } catch (SignatureVerificationException sve) {
            error(resp, sve);
        } catch (TokenExpiredException tee) {
            error(resp, tee);
        }
    }

    // 필터 예외는 Exception 핸들러에서 처리하지 못한다
    public void error(HttpServletResponse resp, Exception e) throws IOException {
        resp.setStatus(401);
        resp.setContentType("application/json; charset=utf-8");
        ResponseDto<?> responseDto = new ResponseDto<>().fail(HttpStatus.UNAUTHORIZED, "인증 안됨", e.getMessage());
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(responseDto);
        resp.getWriter().println(responseBody);
    }
}
