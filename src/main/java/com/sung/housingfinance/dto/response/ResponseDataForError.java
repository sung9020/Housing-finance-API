package com.sung.housingfinance.dto.response;/*
 *
 * @author 123msn
 * @since 2019-04-02
 */

import lombok.Data;
import lombok.Setter;

/**
 * 에러용 응답 출력
 */
@Data
public class ResponseDataForError {
    private String errorCode;
    private String msg;

}
