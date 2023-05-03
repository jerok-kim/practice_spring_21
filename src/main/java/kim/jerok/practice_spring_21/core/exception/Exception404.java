package kim.jerok.practice_spring_21.core.exception;

import kim.jerok.practice_spring_21.dto.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// 리소스 없음
@Getter
public class Exception404 extends RuntimeException {
    public Exception404(String message) {
        super(message);
    }

    public ResponseDto<?> body() {
        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.fail(HttpStatus.NOT_FOUND, "notFound", getMessage());
        return responseDto;
    }

    public HttpStatus status() {
        return HttpStatus.NOT_FOUND;
    }
}
