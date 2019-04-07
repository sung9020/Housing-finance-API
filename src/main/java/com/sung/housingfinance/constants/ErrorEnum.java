package com.sung.housingfinance.constants;
/*
 *
 * @author 123msn
 * @since 2019-04-02
 */

import lombok.Getter;

public enum ErrorEnum {
    SUCCESS("요청에 성공했습니다."),
    RUNTIME_ERROR("서버 에러가 발생했습니다."),
    FORBIDDEN_ERROR("접근이 금지되었습니다. "),
    ALREADY_REGISTERED_USER_ERROR("이미 등록된 유저입니다."),
    ALREADY_REGISTERED_FILE_ERROR("데이터가 이미 등록되었습니다."),
    DATA_INPUT_ERROR("주택금융 지원금액 데이터를 먼저 추가해야 조회 가능합니다."),
    TOKEN_ERROR("유효하지 않은 토큰을 입력했습니다."),
    NOT_FOUND_USER_ERROR("유저 엔티티를 찾을수 없습니다.");

    @Getter
    private final String msg;

    ErrorEnum( String msg) {
        this.msg = msg;
    }
}
