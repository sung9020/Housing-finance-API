package com.sung.housingfinance.dto;/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

import lombok.Data;

import java.util.List;

@Data
public class ResponseDataFor2nd {

    private String name;
    private List<SupportTotalDto> supportDataList;
}
