package com.sung.housingfinance.constants;
/*
 *
 * @author 123msn
 * @since 2019-04-02
 */

import lombok.Getter;

public enum ErrorEnum {
    SUCCESS("1", "요청에 성공했습니다."),
    RUNTIME_ERROR("500", "서버 에러가 발생했습니다."),
    FORBIDDEN_ERROR("403", "접근이 금지되었습니다. "),
    ALREADY_REGISTERED_USER_ERROR("1000", "이미 등록된 유저입니다."),
    ALREADY_REGISTERED_FILE_ERROR("1100", "데이터가 이미 등록되었습니다.");

    @Getter
    private final String errorCode;

    @Getter
    private final String msg;

    ErrorEnum(String errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }
}
