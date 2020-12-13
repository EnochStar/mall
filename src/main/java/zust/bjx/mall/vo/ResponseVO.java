package zust.bjx.mall.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.validation.BindingResult;
import zust.bjx.mall.enums.ResponseEnum;

import java.util.Objects;

/**
 * @author EnochStar
 * @title: ResponseVO
 * @projectName mall
 * @description: TODO
 * @date 2020/12/13 20:37
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseVO<T> {
    private Integer status;
    private String msg;
    private T data;

    public ResponseVO(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static <T> ResponseVO<T> success(String msg) {
        return new ResponseVO<>(ResponseEnum.SUCCESS.getCode(),msg);
    }
    public static <T> ResponseVO<T> success() {
        return new ResponseVO<>(ResponseEnum.SUCCESS.getCode(),ResponseEnum.SUCCESS.getDesc());
    }

    public static <T> ResponseVO<T> error(ResponseEnum responseEnum) {
        return new ResponseVO<>(responseEnum.getCode(),responseEnum.getDesc());
    }

    public static <T> ResponseVO<T> error(ResponseEnum responseEnum,String msg) {
        return new ResponseVO<>(responseEnum.getCode(),msg);
    }
    public static <T> ResponseVO<T> error(ResponseEnum responseEnum, BindingResult bindingResult) {
        return new ResponseVO<>(responseEnum.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getField()+" "+
                bindingResult.getFieldError().getDefaultMessage());
    }
}
