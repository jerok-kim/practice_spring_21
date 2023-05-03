package kim.jerok.practice_spring_21.core.exception;

import kim.jerok.practice_spring_21.dto.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// 서버 에러
@Getter
public class Exception500 extends RuntimeException {
    public Exception500(String message) {
        super(message);
    }

    public ResponseDto body() {
        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.fail(HttpStatus.INTERNAL_SERVER_ERROR, "serverError", getMessage());
        return responseDto;
    }

    public HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
