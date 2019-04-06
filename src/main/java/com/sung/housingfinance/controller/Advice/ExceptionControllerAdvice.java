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
        result.setMsg(ErrorEnum.RUNTIME_ERROR.getMsg());
        result.setErrorCode(ErrorEnum.RUNTIME_ERROR.getErrorCode());
        log.error(ErrorEnum.RUNTIME_ERROR.getMsg());

        return result;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    ResponseData unauthorizedHandleException(AccessDeniedException ex) {
        ResponseData result = new ResponseData();
        result.setMsg(ErrorEnum.FORBIDDEN_ERROR.getMsg());
        result.setErrorCode(ErrorEnum.FORBIDDEN_ERROR.getErrorCode());
        log.error(ErrorEnum.FORBIDDEN_ERROR.getMsg());

        return result;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ResponseData IllegalArgumentHandleException(IllegalArgumentException ex) {
        ResponseData result = new ResponseData();
        result.setMsg(ErrorEnum.DATA_INPUT_ERROR.getMsg());
        result.setErrorCode(ErrorEnum.DATA_INPUT_ERROR.getErrorCode());
        log.error(ErrorEnum.DATA_INPUT_ERROR.getMsg());

        return result;
    }
}
