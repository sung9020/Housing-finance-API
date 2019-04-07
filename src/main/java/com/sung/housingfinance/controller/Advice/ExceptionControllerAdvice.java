package com.sung.housingfinance.controller.Advice;
/*
 *
 * @author 123msn
 * @since 2019-04-02
 */

import com.sung.housingfinance.constants.ErrorEnum;
import com.sung.housingfinance.dto.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ResponseData handleException(Exception ex) {
        ResponseData result = new ResponseData();
        result.setMsg(ErrorEnum.RUNTIME_ERROR.getMsg()); //not custom
        log.error(ex.getMessage());
        return result;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    ResponseData handleUnauthorizedException(AccessDeniedException ex) {
        ResponseData result = new ResponseData();
        result.setMsg(ErrorEnum.FORBIDDEN_ERROR.getMsg()); //not custom
        log.error(ex.getMessage());

        return result;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    ResponseData handleAuthenticationException(AuthenticationException ex) {
        ResponseData result = new ResponseData();
        result.setMsg(ErrorEnum.FORBIDDEN_ERROR.getMsg()); //not custom
        log.error(ex.getMessage());

        return result;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ResponseData handleIllegalArgumentException(IllegalArgumentException ex) {
        ResponseData result = new ResponseData();
        result.setMsg(ex.getMessage());
        log.error(ex.getMessage());

        return result;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseData handleUsernameNotFoundException(UsernameNotFoundException ex) {
        ResponseData result = new ResponseData();
        result.setMsg(ex.getMessage());
        log.error(ex.getMessage());

        return result;
    }
}
