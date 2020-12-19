package zust.bjx.mall.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import zust.bjx.mall.enums.ResponseEnum;
import zust.bjx.mall.vo.ResponseVO;

import java.util.Objects;

/**
 * @author EnochStar
 * @title: RuntimeExceptionHandler
 * @projectName mall
 * @description: 捕获RuntimeException
 * @date 2020/12/14 14:41
 */
@ControllerAdvice
public class RuntimeExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseVO handle(RuntimeException e) {
        return ResponseVO.error(ResponseEnum.ERRPR,e.getMessage());
    }

    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public ResponseVO userLoginHandler() {
        return ResponseVO.error(ResponseEnum.NEED_LOGIN);
    }

    /**
     * 对Post入参做统一的处理
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseVO notValidExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        Objects.requireNonNull(bindingResult.getFieldError());
        return ResponseVO.error(ResponseEnum.PARAM_ERROR,
                bindingResult.getFieldError().getField() + " " + bindingResult.getFieldError().getDefaultMessage());
    }
}
