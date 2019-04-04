package com.sung.housingfinance.service.impl;

import com.sung.housingfinance.constants.ErrorEnum;
import com.sung.housingfinance.constants.FileEnum;
import com.sung.housingfinance.dto.SupportAmountDto;
import com.sung.housingfinance.dto.SupportDataDto;
import com.sung.housingfinance.dto.SupportTotalDto;
import com.sung.housingfinance.dto.response.ResponseData;
import com.sung.housingfinance.dto.response.ResponseDataFor2nd;
import com.sung.housingfinance.dto.response.ResponseDataFor3rd;
import com.sung.housingfinance.dto.response.ResponseDataFor4th;
import com.sung.housingfinance.dto.response.ResponseDataFor5th;
import com.sung.housingfinance.entity.SupportAvg;
import com.sung.housingfinance.entity.SupportSum;
import com.sung.housingfinance.repositoy.SupportDataRepository;
import com.sung.housingfinance.service.SupportDataInterface;
import com.sung.housingfinance.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
 *
 * @author 123msn
 * @since 2019-04-03
 */
@Service
public class SupportDataService implements SupportDataInterface {

    @Autowired
    private SupportDataRepository supportDataRepository;

    private final String TITLE = "주택금융 공급현황";


    @Override
    public ResponseData setSupportData() throws Exception {
        ResponseData responseData = new ResponseData();
        responseData.setErrorCode(ErrorEnum.SUCCESS.getErrorCode());
        responseData.setMsg(ErrorEnum.SUCCESS.getMsg());

        File file = new File("housing_finance_support_data.csv");
        List<List<String>> fileData = FileUtils.readCsv(file);
        SupportDataDto supportDataDto = new SupportDataDto();
        int rowSize = fileData.size();
        FileEnum[] fileEnums = FileEnum.values();

        for(int i = 1; i < rowSize; i++){
            List<String> row = fileData.get(i);
            supportDataDto.setYear(Integer.valueOf(row.get(FileEnum.YEAR.getCol())));
            supportDataDto.setMonth(Integer.valueOf(row.get(FileEnum.MONTH.getCol())));

            for(FileEnum fileEnum : fileEnums){
                if(fileEnum.getCol() > FileEnum.MONTH.getCol()){
                    supportDataDto.setInstituteName(fileEnum.getInstitute_name());
                    supportDataDto.setInstituteCode(fileEnum.getInstitute_code());
                    supportDataDto.setSupportValue(Integer.valueOf(row.get(fileEnum.getCol())));
                    supportDataRepository.save(supportDataDto.toEntity());
                }
            }
        }

        return responseData;
    }

    @Override
    public ResponseDataFor2nd getSupportSum() {
        ResponseDataFor2nd response = new ResponseDataFor2nd();

        List<SupportSum> supportSumList = supportDataRepository.findBySupportSum();
        List<SupportTotalDto> supportTotalList = new ArrayList<>();
        List<Integer> yearArray = new ArrayList<>();

        for(SupportSum supportOperation : supportSumList){
            int year = supportOperation.getYear();
            String instituteName = supportOperation.getInstituteName();
            long supportSum = supportOperation.getSupportSum();

            if(!yearArray.contains(year)){

                SupportTotalDto supportTotal = new SupportTotalDto();
                Map<String, Long> detail_amount = new HashMap<>();
                detail_amount.put(instituteName, supportSum);
                yearArray.add(year);

                supportTotal.setDetail_amount(detail_amount);
                supportTotal.setYear(year);
                supportTotal.setTotal_amount(supportTotal.getTotal_amount() + supportSum);
                supportTotalList.add(supportTotal);
            }else{
                SupportTotalDto supportTotal = supportTotalList.stream().filter( o -> o.getYear() == year).findFirst().get();
                Map<String, Long> detail_amount = supportTotal.getDetail_amount();
                detail_amount.put(instituteName, supportSum);

                supportTotal.setYear(year);
                supportTotal.setTotal_amount(supportTotal.getTotal_amount() + supportSum);
                supportTotalList.add(supportTotal);
            }
        }

        response.setName(TITLE);
        response.setSupportTotalList(supportTotalList);

        return response;
    }

    @Override
    public ResponseDataFor3rd getMaxOfSupportSum() {

        ResponseDataFor3rd response = new ResponseDataFor3rd();

        List<SupportSum> supportSumList = supportDataRepository.findBySupportSum();
        SupportSum maxSupport = supportSumList.stream().max(Comparator.comparing(SupportSum::getSupportSum)).get();

        response.setBank(maxSupport.getInstituteName());
        response.setYear(String.valueOf(maxSupport.getYear()));

        return response;
    }

    @Override
    public ResponseDataFor4th getMaxMinOfSupportAvg(String bankName) {

        ResponseDataFor4th response = new ResponseDataFor4th();

        List<SupportAvg> supportAvgList = supportDataRepository.findBySupportAvg(bankName);
        List<SupportAmountDto> supportAmountList = new ArrayList<>();
        SupportAmountDto supportMax = new SupportAmountDto();
        SupportAmountDto supportMin = new SupportAmountDto();

        Optional<SupportAvg> supportMaxOptional =supportAvgList.stream().max(Comparator.comparing(SupportAvg::getSupportAvg));
        supportMaxOptional.ifPresent(supportAvg -> {
            supportMax.setYear(supportAvg.getYear());
            supportMax.setAmount(Math.round(supportAvg.getSupportAvg()));
            supportAmountList.add(supportMax);
        });

        Optional<SupportAvg> supportMinOptional =supportAvgList.stream().min(Comparator.comparing(SupportAvg::getSupportAvg));
        supportMinOptional.ifPresent(supportAvg -> {
            supportMin.setYear(supportAvg.getYear());
            supportMin.setAmount(Math.round(supportAvg.getSupportAvg()));
            supportAmountList.add(supportMin);
        });

        response.setBank(bankName);
        response.setSupport_amount(supportAmountList);

        return response;
    }

    @Override
    public ResponseDataFor5th getPredictedSupportData() {
        return null;
    }
}
