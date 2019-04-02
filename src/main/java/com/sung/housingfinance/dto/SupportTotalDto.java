package com.sung.housingfinance.dto;/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

import lombok.Data;

import java.util.Map;

@Data
public class SupportTotalDto {

    private int year;
    private long total_amount;
    private Map<String, Long> detail_amount;

}
