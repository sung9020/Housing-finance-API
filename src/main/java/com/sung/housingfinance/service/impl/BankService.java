package com.sung.housingfinance.service.impl;

import com.sung.housingfinance.dto.BankDataDto;
import com.sung.housingfinance.dto.response.ResponseDataFor1st;
import com.sung.housingfinance.entity.Bank;
import com.sung.housingfinance.repositoy.BankDataRepository;
import com.sung.housingfinance.service.BankInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.assertj.core.util.Streams;

import java.util.List;
import java.util.stream.Collectors;

/*
 *
 * @author 123msn
 * @since 2019-04-03
 */
@Service
public class BankService implements BankInterface {

    @Autowired
    private BankDataRepository bankDataRepository;

    @Override
    public ResponseDataFor1st getBankList() {

        ResponseDataFor1st response = new ResponseDataFor1st();

        Iterable<Bank> bankDataIterable = bankDataRepository.findAll();
        List<BankDataDto> bankList = Streams.stream(bankDataIterable).map(BankDataDto::new).collect(Collectors.toList());

        response.setBankList(bankList);

        return response;
    }



}
