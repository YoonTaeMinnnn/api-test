package testApi.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.*;

@Slf4j
@RestController
public class ApiController {


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ErrorBody> error(MethodArgumentNotValidException e) {
        List<ErrorBody> errorList = new ArrayList<>();

        //String code = e.getBindingResult().getAllErrors().get(0).getCode();
        //String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        String code = "";
        String message = "";

        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            code = error.getCode();
            message = error.getDefaultMessage();
            errorList.add(new ErrorBody(code, message));
        }
        return errorList;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorBody error(HttpMessageNotReadableException e) {

        return new ErrorBody("typeError", "타입이 맞지 않습니다");
    }

    @PostMapping("/test")
    public String test(@Validated @RequestBody Member member) {
        return "ok";
    }


    @AllArgsConstructor
    @Getter @Setter
    static class ErrorBody{
        private String code;
        private String message;
    }

    @Getter
    @Setter
    static class Member {

        @Max(value = 1000, message = "1000이하여야 합니다.")
        private int id;

        @NotNull(message = "null값은 허용하지 않습니다.")
        private String name;
    }


}
