package com.sung.housingfinance.dto.response;/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

import com.sung.housingfinance.dto.SupportAmountDto;
import lombok.Data;

import java.util.List;

/**
 * 외환 은행의 평균 지원 금액 중 가장 큰 값과 작은값 출력
 */
@Data
public class ResponseDataFor4th {

    private String bank;
    private List<SupportAmountDto> support_amount;
}
