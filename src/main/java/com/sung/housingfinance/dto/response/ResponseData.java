package com.sung.housingfinance.dto.response;
/*
 * @author 123msn
 * @since 2019-04-03
 */

import lombok.Data;
import lombok.Setter;
/**
 *  요청시 기본 출력
 */
@Data
public class ResponseData {
    private String errorCode;
    private String msg;
}
