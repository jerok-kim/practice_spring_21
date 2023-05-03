package kim.jerok.practice_spring_21.core.exception;

import kim.jerok.practice_spring_21.dto.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// 권한 없음
@Getter
public class Exception403 extends RuntimeException {
    public Exception403(String message) {
        super(message);
    }

    public ResponseDto<?> body() {
        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.fail(HttpStatus.FORBIDDEN, "forbidden", getMessage());
        return responseDto;
    }

    public HttpStatus status() {
        return HttpStatus.FORBIDDEN;
    }
}
