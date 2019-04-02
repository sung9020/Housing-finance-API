package com.sung.housingfinance;

import com.sung.housingfinance.constants.FileEnum;
import com.sung.housingfinance.dto.BankDataDto;
import com.sung.housingfinance.dto.SupportDataDto;
import com.sung.housingfinance.dto.SupportTotalDto;
import com.sung.housingfinance.entity.Bank;
import com.sung.housingfinance.entity.SupportAvg;
import com.sung.housingfinance.entity.SupportData;
import com.sung.housingfinance.entity.SupportSum;
import com.sung.housingfinance.repositoy.BankDataRepository;
import com.sung.housingfinance.repositoy.SupportDataRepository;
import com.sung.housingfinance.utils.FileUtils;
import org.assertj.core.util.Streams;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "local")
public class BasicFeatureTests {

    @Autowired
    private SupportDataRepository supportDataRepository;

    @Autowired
    private BankDataRepository bankDataRepository;

    @InjectMocks
    private FileUtils fileUtils;

    List<BankDataDto> bankList;

    @Before
    public void setup() throws Exception{
        File file = new File("housing_finance_support_data.csv");
        List<List<String>> fileData = fileUtils.readCsv(file);
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
    }

    @After
    public void tearDown() {
        supportDataRepository.deleteAll();
        bankDataRepository.deleteAll();
    }


    @Test
    public void csv_읽기_Test() throws Exception{
        File file = new File("housing_finance_support_data.csv");
        List<List<String>> supportData = fileUtils.readCsv(file);

        assertThat(supportData, Matchers.notNullValue());
        assertThat(supportData, Matchers.hasSize(155));
    }

    @Test
    public void 은행명_은행코드_조회_Test() {
        Iterable<Bank> bankDataIterable = bankDataRepository.findAll();
        List<BankDataDto> bankList = Streams.stream(bankDataIterable).map(BankDataDto::new).collect(Collectors.toList());

        assertThat(bankList, Matchers.hasSize(9));
        assertThat(bankList.get(0).getInstitute_name(), Matchers.is("주택도시기금"));
        assertThat(bankList.get(1).getInstitute_name(), Matchers.is("국민은행"));
        assertThat(bankList.get(2).getInstitute_name(), Matchers.is("우리은행"));
        assertThat(bankList.get(3).getInstitute_name(), Matchers.is("신한은행"));
        assertThat(bankList.get(4).getInstitute_code(), Matchers.is("BNK027"));
        assertThat(bankList.get(5).getInstitute_code(), Matchers.is("BNK025"));
        assertThat(bankList.get(6).getInstitute_code(), Matchers.is("BNK012"));
        assertThat(bankList.get(7).getInstitute_code(), Matchers.is("BNK005"));
        assertThat(bankList.get(8).getInstitute_code(), Matchers.is("BNK051"));
    }

    @Test
    public void 은행_주택금융_지원금액_모든데이터조회_Test(){
        Iterable<SupportData> supportDataIterable = supportDataRepository.findAll();
        List<SupportDataDto> supportDataList = Streams.stream(supportDataIterable).map(SupportDataDto::new).collect(Collectors.toList());

        assertThat(supportDataList, Matchers.notNullValue());
        assertThat(supportDataList, Matchers.hasSize(1694));
    }


    @Test
    public void 연도별_은행별_지원금액합계_Test() {
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
                yearArray.add(year);

                detail_amount.put(instituteName, supportSum);
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

        SupportTotalDto testData = new SupportTotalDto();

        for(SupportTotalDto temp : supportTotalList){
            if(temp.getYear() == 2014){
                testData = temp;
            }
        }

        assertThat(testData.getYear(), Matchers.is(2014));
        assertThat(testData.getDetail_amount(), Matchers.hasKey("우리은행"));
        assertThat(testData.getTotal_amount(), Matchers.is(318771L));
    }

    @Test
    public void getMaxSupportSumTest(){
        List<SupportSum> supportSumList = supportDataRepository.findBySupportSum();
        SupportSum maxSupport = supportSumList.stream().max(Comparator.comparing(SupportSum::getSupportSum)).get();

        assertThat(maxSupport.getInstituteName(), Matchers.is("주택도시기금"));
        assertThat(maxSupport.getSupportSum(), Matchers.is(96184L));
        assertThat(maxSupport.getYear(),Matchers.is(2014));
    }

    @Test
    public void getKEBMinMaxDataTest(){
        List<SupportAvg> supportAvgList = supportDataRepository.findBySupportAvg("외환은행");
        Long max = Math.round(supportAvgList.stream().max(Comparator.comparing(SupportAvg::getSupportAvg)).get().getSupportAvg());
        Long min = Math.round(supportAvgList.stream().min(Comparator.comparing(SupportAvg::getSupportAvg)).get().getSupportAvg());

        assertThat(max, Matchers.is(1702L));
        assertThat(min, Matchers.is(0L));
    }

    @Test
    public void getPredictedSupportDataTest(){


    }

}
