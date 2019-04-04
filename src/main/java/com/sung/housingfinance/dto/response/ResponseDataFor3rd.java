package com.sung.housingfinance.dto.response;/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

import lombok.Data;
import lombok.Setter;

/**
 * 연도별 지원금액이 가장 큰 은행, 연도 출력
 */
@Data
public class ResponseDataFor3rd {
    private String year;
    private String bank;
}
