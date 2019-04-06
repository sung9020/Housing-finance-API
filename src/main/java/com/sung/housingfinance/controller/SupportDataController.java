package com.sung.housingfinance.controller;
/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

import com.sung.housingfinance.dto.response.ResponseData;
import com.sung.housingfinance.dto.response.ResponseDataFor2nd;
import com.sung.housingfinance.dto.response.ResponseDataFor3rd;
import com.sung.housingfinance.dto.response.ResponseDataFor4th;
import com.sung.housingfinance.dto.response.ResponseDataFor5th;
import com.sung.housingfinance.service.SupportDataInterface;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/supportData")
public class SupportDataController {

    @Autowired
    SupportDataInterface supportDataInterface;

    @PostMapping("")
    @ApiOperation(value ="주택금융 지원금액 데이터 삽입 API")
    public ResponseData setSupportData() throws Exception{
        ResponseData response = supportDataInterface.setSupportData();

        return response;
    }

    @GetMapping("ALL")
    @ApiOperation(value ="주택금융 지원금액 현황 조회 API")
    public ResponseDataFor2nd getSupportData() {
        ResponseDataFor2nd response = supportDataInterface.getSupportSum();

        return response;
    }

    @GetMapping("ALL/max")
    @ApiOperation(value ="지원 금액 현황 중 연도별 최대 지원 금액에 해당하는 은행, 연도 조회 API")
    public ResponseDataFor3rd getMaxOfSupportSum(){
        ResponseDataFor3rd response = supportDataInterface.getMaxOfSupportSum();

        return response;
    }

    @GetMapping("KEB/minMax")
    @ApiOperation(value ="외환은행의 연도별 지원금액 최대/최소값 조회 API")
    public ResponseDataFor4th getMaxMinOfSupportAvg()  {
        final String KEB_BANK = "외환은행";

        ResponseDataFor4th response = supportDataInterface.getMaxMinOfSupportAvg(KEB_BANK);

        return response;
    }

    @GetMapping("ALL/next/{bank}/{month}")
    @ApiOperation(value ="2018년도 특정은행 특정달의 지원금액 예측 API")
    public ResponseDataFor5th getPredictedSupportData(@ApiParam(required = true, name="bank", value = "은행 이름") @PathVariable("bank") String bank,
                                                      @ApiParam(required = true, name="month", value = "월 입력") @PathVariable("month") int month ) throws Exception {

        ResponseDataFor5th response = supportDataInterface.getPredictedSupportData(bank, month);

        return response;
    }

}
