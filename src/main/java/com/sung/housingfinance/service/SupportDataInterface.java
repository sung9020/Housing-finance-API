package com.sung.housingfinance.service;

import com.sung.housingfinance.dto.response.ResponseData;
import com.sung.housingfinance.dto.response.ResponseDataFor2nd;
import com.sung.housingfinance.dto.response.ResponseDataFor3rd;
import com.sung.housingfinance.dto.response.ResponseDataFor4th;
import com.sung.housingfinance.dto.response.ResponseDataFor5th;

/*
 *
 * @author 123msn
 * @since 2019-04-03
 */
public interface SupportDataInterface {

    ResponseData setSupportData() throws Exception;

    ResponseDataFor2nd getSupportSum();

    ResponseDataFor3rd getMaxOfSupportSum();

    ResponseDataFor4th getMaxMinOfSupportAvg(String bankName);

    ResponseDataFor5th getPredictedSupportData();
}
