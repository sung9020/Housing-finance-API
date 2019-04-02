package com.sung.housingfinance.controller.Advice;
/*
 *
 * @author 123msn
 * @since 2019-04-02
 */

import com.sung.housingfinance.constants.ErrorEnum;
import com.sung.housingfinance.dto.ResponseDataForError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ResponseDataForError handleException(Exception ex) {
        ResponseDataForError result = new ResponseDataForError();
        result.setMsg(ErrorEnum.RUNTIME_ERROR.getMsg());
        result.setErrorCode(ErrorEnum.RUNTIME_ERROR.getErrorCode());
        log.error("런타임 에러 발생");

        return result;
    }

}
