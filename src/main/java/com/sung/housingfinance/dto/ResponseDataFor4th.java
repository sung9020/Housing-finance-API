package com.sung.housingfinance.dto;/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

import lombok.Data;

import java.util.List;

@Data
public class ResponseDataFor4th {

    String bank;
    List<SupportAmountDto> support_amount;
}
