package com.sung.housingfinance.dto.response;/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

import com.sung.housingfinance.dto.BankDataDto;
import lombok.Data;
import lombok.Setter;

import java.util.List;

/**
 * 주택금융 공급 금융기관 목륵 출력
 */
@Data
public class ResponseDataFor1st {
    private List<BankDataDto> bankList;
}
