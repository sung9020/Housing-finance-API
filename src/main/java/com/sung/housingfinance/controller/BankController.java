package com.sung.housingfinance.controller;

import com.sung.housingfinance.dto.response.ResponseData;
import com.sung.housingfinance.dto.response.ResponseDataFor1st;
import com.sung.housingfinance.service.BankInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/*
 *
 * @author 123msn
 * @since 2019-04-03
 */

@RestController
@RequestMapping("bank")
public class BankController {

    @Autowired
    BankInterface bankInterface;


    @GetMapping("")
    public ResponseDataFor1st bankList(){

        ResponseDataFor1st response = bankInterface.getBankList();

        return response;
    }
}
