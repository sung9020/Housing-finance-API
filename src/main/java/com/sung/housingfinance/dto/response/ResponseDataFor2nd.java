package com.sung.housingfinance.dto.response;/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

import com.sung.housingfinance.dto.SupportTotalDto;
import lombok.Data;
import lombok.Setter;

import java.util.List;

/**
 * 주택금융 공급현황
 */
@Data
public class ResponseDataFor2nd {

    private String name;
    private List<SupportTotalDto> supportTotalList;
}
