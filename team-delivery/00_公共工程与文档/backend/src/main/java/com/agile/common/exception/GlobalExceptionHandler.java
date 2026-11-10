package com.agile.common.exception;

import com.agile.common.result.R;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public R<String> handleValidException(Exception e){
        String msg = "参数校验失败";
        if(e instanceof MethodArgumentNotValidException){
            msg = ((MethodArgumentNotValidException)e).getBindingResult().getFieldError().getDefaultMessage();
        }
        return R.fail(msg);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public R<String> handleConstraintException(ConstraintViolationException e){
        return R.fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R<String> handleException(Exception e){
        e.printStackTrace();
        return R.fail("系统异常：" + e.getMessage());
    }
}
