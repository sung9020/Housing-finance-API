package com.sung.housingfinance.service;

/*
 *
 * @author 123msn
 * @since 2019-04-03
 */

import com.sung.housingfinance.dto.response.ResponseDataFor1st;

public interface BankInterface {

    ResponseDataFor1st getBankList();

    String getBankCode(String bankName);
}
