package com.sung.housingfinance.dto;/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

import lombok.Data;

/**
 * 지원 금액, 연도 DTO
 */
@Data
public class SupportAmountDto {
    private int year;
    private Long amount;
}
