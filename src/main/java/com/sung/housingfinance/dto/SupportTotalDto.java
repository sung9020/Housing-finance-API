package com.sung.housingfinance.dto;/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 지원 금액 현황 DTO
 */
@Data
public class SupportTotalDto {

    private int year;
    private long total_amount;
    private Map<String, Long> detail_amount;


    public SupportTotalDto() {
        this.year = 0;
        this.total_amount = 0;
        this.detail_amount = new HashMap<>();
    }
}
