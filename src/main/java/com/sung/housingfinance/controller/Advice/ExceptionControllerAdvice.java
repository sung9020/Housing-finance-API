package com.sung.housingfinance.controller.Advice;
/*
 *
 * @author 123msn
 * @since 2019-04-02
 */

import com.sung.housingfinance.constants.ErrorEnum;
import com.sung.housingfinance.dto.response.ResponseDataForError;
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
    ResponseDataForError handleException(Exception ex) {
        ResponseDataForError result = new ResponseDataForError();
        result.setMsg(ErrorEnum.RUNTIME_ERROR.getMsg());
        result.setErrorCode(ErrorEnum.RUNTIME_ERROR.getErrorCode());
        log.error(ErrorEnum.RUNTIME_ERROR.getMsg());

        return result;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    ResponseDataForError unauthorizedHandleException(AccessDeniedException ex) {
        ResponseDataForError result = new ResponseDataForError();
        result.setMsg(ErrorEnum.FORBIDDEN_ERROR.getMsg());
        result.setErrorCode(ErrorEnum.FORBIDDEN_ERROR.getErrorCode());
        log.error(ErrorEnum.FORBIDDEN_ERROR.getMsg());

        return result;
    }

}
