package com.sung.housingfinance.dto.request;/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

import lombok.Data;

/**
 * 특정 은행의 특정 달에 대한 금융 지원 금액 예측(2018년) 요청
 */
@Data
public class RequestDataFor5th {
    private String bank;
    private String month;

}
