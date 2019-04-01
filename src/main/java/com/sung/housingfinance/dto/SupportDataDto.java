package com.sung.housingfinance.dto;/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

import lombok.Data;

import java.util.Map;

@Data
public class SupportDataDto {

    private int year;
    private String total_amount;
    private Map<String, String> detail_amount;

}
