package com.sung.housingfinance.dto.response;/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

import lombok.Data;
import lombok.Setter;

/**
 * 특정 은행의 특정 달에 대한 금융 지원 금액 예측(2018년) 출력
 */
@Data
public class ResponseDataFor5th {
    private String bank;
    private String year;
    private String month;
    private String amount;
}
