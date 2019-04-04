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
import com.sung.housingfinance.service.SupportDataInterface;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("supportData")
public class SupportDataController {

    @Autowired
    SupportDataInterface supportDataInterface;

    @PostMapping("")
    public ResponseData setSupportData() throws Exception{
        ResponseData response = supportDataInterface.setSupportData();

        return response;
    }

    @GetMapping("ALL")
    public ResponseDataFor2nd getSupportData() {
        ResponseDataFor2nd response = supportDataInterface.getSupportSum();

        return response;
    }

    @GetMapping("ALL/max")
    public ResponseDataFor3rd getMaxOfSupportSum() {
        ResponseDataFor3rd response = supportDataInterface.getMaxOfSupportSum();

        return response;
    }

    @GetMapping("KEB/minMax")
    public ResponseDataFor4th getMaxMinOfSupportAvg() {
        final String KEB_BANK = "외환은행";

        ResponseDataFor4th response = supportDataInterface.getMaxMinOfSupportAvg(KEB_BANK);

        return response;
    }


}
