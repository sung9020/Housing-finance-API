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
import com.sung.housingfinance.entity.SupportData;
import com.sung.housingfinance.entity.SupportSum;
import com.sung.housingfinance.repositoy.SupportDataRepository;
import com.sung.housingfinance.service.PolyCurveFittingInterface;
import com.sung.housingfinance.service.SupportDataInterface;
import com.sung.housingfinance.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 *
 * @author 123msn
 * @since 2019-04-03
 */
@Service
public class SupportDataService implements SupportDataInterface {

    @Autowired
    private SupportDataRepository supportDataRepository;

    @Autowired
    private BankService bankService;

    private final String TITLE = "주택금융 공급현황";


    @Override
    @Transactional
    public ResponseData setSupportData() throws Exception {
        ResponseData responseData = new ResponseData();

        List<SupportData> supportDataIterable = supportDataRepository.findAll();

        if(supportDataIterable.size() > 0){
            responseData.setMsg(ErrorEnum.ALREADY_REGISTERED_FILE_ERROR.getMsg());

        }else{
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
                        supportDataDto.setInstituteCode(bankService.getBankCode(fileEnum.getInstitute_name()));
                        supportDataDto.setSupportValue(Integer.valueOf(row.get(fileEnum.getCol())));
                        supportDataRepository.save(supportDataDto.toEntity());
                    }
                }
            }

            responseData.setMsg(ErrorEnum.SUCCESS.getMsg());
        }

        return responseData;
    }

    @Override
    public ResponseDataFor2nd getSupportSum()  {
        ResponseDataFor2nd response = new ResponseDataFor2nd();

        List<SupportSum> supportSumList = supportDataRepository.findBySupportSum();
        if(supportSumList.size() < 1){
            throw new IllegalArgumentException(ErrorEnum.DATA_INPUT_ERROR.getMsg());
        }

        List<SupportTotalDto> supportTotalList = new ArrayList<>();
        List<Integer> yearArray = new ArrayList<>();

        for(SupportSum supportOperation : supportSumList){
            int year = supportOperation.getYear();
            String instituteName = supportOperation.getInstituteName();
            long sum = supportOperation.getSum();

            if(!yearArray.contains(year)){
                SupportTotalDto supportTotal = new SupportTotalDto();
                Map<String, Long> detail_amount = new HashMap<>();
                detail_amount.put(instituteName, sum);
                yearArray.add(year);

                supportTotal.setDetail_amount(detail_amount);
                supportTotal.setYear(year);
                supportTotal.setTotal_amount(supportTotal.getTotal_amount() + sum);
                supportTotalList.add(supportTotal);
            }else{
                SupportTotalDto supportTotal = supportTotalList.stream().filter( o -> o.getYear() == year).findFirst().get();
                Map<String, Long> detail_amount = supportTotal.getDetail_amount();
                detail_amount.put(instituteName, sum);

                supportTotal.setYear(year);
                supportTotal.setTotal_amount(supportTotal.getTotal_amount() + sum);
            }
        }
        //sort
        supportTotalList = supportTotalList.stream().sorted(Comparator.comparing(SupportTotalDto::getYear)).collect(Collectors.toList());

        response.setName(TITLE);
        response.setSupportTotalList(supportTotalList);

        return response;
    }

    @Override
    public ResponseDataFor3rd getMaxOfSupportSum() {

        ResponseDataFor3rd response = new ResponseDataFor3rd();

        List<SupportSum> supportSumList = supportDataRepository.findBySupportSum();
        if(supportSumList.size() < 1){
            throw new IllegalArgumentException(ErrorEnum.DATA_INPUT_ERROR.getMsg());
        }

        SupportSum maxSupport = supportSumList.stream().max(Comparator.comparing(SupportSum::getSum)).get();

        response.setBank(maxSupport.getInstituteName());
        response.setYear(String.valueOf(maxSupport.getYear()));

        return response;
    }

    @Override
    public ResponseDataFor4th getMaxMinOfSupportAvg(String bankName)  {

        ResponseDataFor4th response = new ResponseDataFor4th();

        List<SupportAvg> supportAvgList = supportDataRepository.findBySupportAvg(bankName);
        if(supportAvgList.size() < 1){
            throw new IllegalArgumentException(ErrorEnum.DATA_INPUT_ERROR.getMsg());
        }

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
    public ResponseDataFor5th getPredictedSupportData(String bank, int month) {

            ResponseDataFor5th response = new ResponseDataFor5th();
            final int predictedYear = 2018;
            String bankCode = bankService.getBankCode(bank);

            PolyCurveFittingInterface polyCurveFittingInterface = new PolyCurveFitting(2); // 2차원 다항식으로 커브피팅한다. y = a1 + a2 * x + a3 * x^2
            List<SupportData> supportDataList = supportDataRepository.findByInstituteNameAndMonth(bank, month);
            if(supportDataList.size() < 1){
                throw new IllegalArgumentException(ErrorEnum.DATA_INPUT_ERROR.getMsg());
            }

            int dataSize = supportDataList.size();
            double[] y = new double[dataSize]; // 반드시 x, y 배열 사이즈가 같아야 한다. 그래야 해당하는 x,y축 매트릭스를 구현.
            double[] x = new double[dataSize];
            for(int i = 1; i < dataSize + 1; i++) { // 일정 간격으로 증가하는 다음에 나올 데이터만 예측하면 되는 것이므로, 1씩 더해주면 다음,다음을 계속 구할수있음.
                x[i - 1] = i; // i = 1 ~ size
            }
            for(int j = 0; j < dataSize; j++){
                y[j] = supportDataList.get(j).getSupportValue();
            }
            polyCurveFittingInterface.setData(y,x);
            double predictedY = polyCurveFittingInterface.predictData(x.length + 1); // 다음 인덱스에 해당하는 값은?

            response.setBank(bankCode);
            response.setAmount(String.valueOf(Math.round(predictedY)));
            response.setYear(String.valueOf(predictedYear));
            response.setMonth(String.valueOf(month));


        return response;
    }
}
