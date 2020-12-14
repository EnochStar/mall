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

    private ResponseVO(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ResponseVO(Integer status, T data) {
        this.status = status;
        this.data = data;
    }
    public static <T> ResponseVO<T> success(T data) {
        return new ResponseVO<>(ResponseEnum.SUCCESS.getCode(),data);
    }
    public static <T> ResponseVO<T> successByMsg(String msg) {
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
