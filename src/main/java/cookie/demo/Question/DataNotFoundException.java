package cookie.demo.Question;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends  RuntimeException{
    private static final long serialVersionUID = 1L;
    public DataNotFoundException(String message) {
        super(message);
    }

    //잘못된 주소를 주면
    // 스프링 부트는 설정된 HTTP 상태 코드(HttpStatus.NOT_FOUND)와
    // 이유("entity not found")를 포함한 응답을 주는것
}
