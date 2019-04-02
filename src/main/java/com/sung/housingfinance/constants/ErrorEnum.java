package com.sung.housingfinance.constants;
/*
 *
 * @author 123msn
 * @since 2019-04-02
 */

import lombok.Getter;

public enum ErrorEnum {
    SUCCESS(1, "요청에 성공했습니다."),
    RUNTIME_ERROR(1000, "서버 에러가 발생했습니다.");


    @Getter
    private final int errorCode;

    @Getter
    private final String msg;

    ErrorEnum(int errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }
}
