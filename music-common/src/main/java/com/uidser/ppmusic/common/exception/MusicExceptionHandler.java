package com.uidser.ppmusic.common.exception;

import com.uidser.ppmusic.common.r.R;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class MusicExceptionHandler {

    @ExceptionHandler(value = MusicException.class)
    public R handlerGlobalException(MusicException musicException) {
        return new R().error().setCode(musicException.getCode()).setMsg(musicException.getMsg());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handlerValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<String> errorList = new ArrayList<>();
        for (FieldError fieldError: fieldErrors) {
            errorList.add(fieldError.getDefaultMessage());
        }
        String join = String.join("；", errorList);
        return new R().error().setMsg(join);
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    public R handlerTokenExpiredException(ExpiredJwtException expiredJwtException) {
        return new R().error().setMsg("登陆过期，请重新登录").setCode(0);
    }

}
