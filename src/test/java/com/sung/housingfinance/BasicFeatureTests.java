package com.sung.housingfinance;

import com.sung.housingfinance.constants.FileEnum;
import com.sung.housingfinance.dto.BankDataDto;
import com.sung.housingfinance.dto.SupportAmountDto;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class BasicFeatureTests {

    @Autowired
    private SupportDataRepository supportDataRepository;

    @Autowired
    private BankDataRepository bankDataRepository;

    @Before
    public void setup() throws Exception{
        setFileList();
        setBankList();
    }

    @After
    public void tearDown() {
        supportDataRepository.deleteAll();
        bankDataRepository.deleteAll();
    }


    @Test
    public void csv_읽기_Test() throws Exception{
        File file = new File("housing_finance_support_data.csv");
        List<List<String>> supportData = FileUtils.readCsv(file);

        assertThat(supportData, Matchers.notNullValue());
        assertThat(supportData, Matchers.hasSize(155));
    }

    @Test
    public void 은행이름으로_은행코드_찾기_Test() {
        String sampleBank = "우리은행";
        Iterable<Bank> bankDataIterable = bankDataRepository.findAll();
        String resultBankCode = "";
        for(Bank bank : bankDataIterable){
            if(sampleBank.equals(bank.getInstituteName())){
                resultBankCode = bank.getInstituteCode();
            }
        }

        assertThat(resultBankCode, Matchers.notNullValue());
        assertThat(resultBankCode, Matchers.equalTo("BNK020"));

    }

    @Test
    public void 은행명_은행코드_조회_Test() {
        Iterable<Bank> bankDataIterable = bankDataRepository.findAll();

        List<BankDataDto> bankList = StreamSupport.stream(bankDataIterable.spliterator(),false).map(BankDataDto::new).collect(Collectors.toList());

        assertThat(bankList, Matchers.hasSize(9));
        assertThat(bankList.get(0).getInstitute_name(), Matchers.equalTo("주택도시기금"));
        assertThat(bankList.get(1).getInstitute_name(), Matchers.equalTo("국민은행"));
        assertThat(bankList.get(2).getInstitute_name(), Matchers.equalTo("우리은행"));
        assertThat(bankList.get(3).getInstitute_name(), Matchers.equalTo("신한은행"));
        assertThat(bankList.get(4).getInstitute_code(), Matchers.equalTo("BNK027"));
        assertThat(bankList.get(5).getInstitute_code(), Matchers.equalTo("BNK025"));
        assertThat(bankList.get(6).getInstitute_code(), Matchers.equalTo("BNK012"));
        assertThat(bankList.get(7).getInstitute_code(), Matchers.equalTo("BNK005"));
        assertThat(bankList.get(8).getInstitute_code(), Matchers.equalTo("BNK051"));
    }

    @Test
    public void 은행_주택금융_지원금액_모든데이터조회_Test(){
        Iterable<SupportData> supportDataIterable = supportDataRepository.findAll();
        List<SupportDataDto> supportDataList = Streams.stream(supportDataIterable).map(SupportDataDto::new).collect(Collectors.toList());

        assertThat(supportDataList, Matchers.notNullValue());
        assertThat(supportDataList, Matchers.hasSize(154 * 9)); // data row * col
    }


    @Test
    public void 연도별_은행별_지원금액합계_Test() {
        List<SupportSum> supportSumList = supportDataRepository.findBySupportSum();
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

        SupportTotalDto testData = new SupportTotalDto();

        for(SupportTotalDto temp : supportTotalList){
            if(temp.getYear() == 2014){
                testData = temp;
                break;
            }
        }

        assertThat(testData.getYear(), Matchers.equalTo(2014));
        assertThat(testData.getDetail_amount(), Matchers.hasKey("우리은행"));
        assertThat(testData.getTotal_amount(), Matchers.equalTo(318771L));
    }

    @Test
    public void 최대지원금액_은행_연도_구하기_Test(){
        List<SupportSum> supportSumList = supportDataRepository.findBySupportSum();
        SupportSum maxSupport = supportSumList.stream().max(Comparator.comparing(SupportSum::getSum)).get();

        assertThat(maxSupport.getInstituteName(), Matchers.equalTo("주택도시기금"));
        assertThat(maxSupport.getSum(), Matchers.equalTo(96184L));
        assertThat(maxSupport.getYear(),Matchers.equalTo(2014));
    }

    @Test
    public void 외환은행_최대최소_지원금구하기_Test(){
        List<SupportAvg> supportAvgList = supportDataRepository.findBySupportAvg("외환은행");
        SupportAmountDto supportMax = new SupportAmountDto();
        SupportAmountDto supportMin = new SupportAmountDto();

        Optional<SupportAvg> supportMaxOptional =supportAvgList.stream().max(Comparator.comparing(SupportAvg::getSupportAvg));
        supportMaxOptional.ifPresent(supportAvg -> {
            supportMax.setYear(supportAvg.getYear());
            supportMax.setAmount(Math.round(supportAvg.getSupportAvg()));
        });

        Optional<SupportAvg> supportMinOptional =supportAvgList.stream().min(Comparator.comparing(SupportAvg::getSupportAvg));
        supportMinOptional.ifPresent(supportAvg -> {
            supportMin.setYear(supportAvg.getYear());
            supportMin.setAmount(Math.round(supportAvg.getSupportAvg()));
        });

        assertThat(supportMax.getAmount(), Matchers.equalTo(1702L));
        assertThat(supportMin.getAmount(), Matchers.equalTo(0L));
    }

    @Test
    public void getPredictedSupportDataTest(){


    }

    private String getBankCode(String bankName){
            Iterable<Bank> bankDataIterable = bankDataRepository.findAll();
            String bankCode = "";
            for(Bank bank : bankDataIterable){
                if(bankName.equals(bank.getInstituteName())){
                    bankCode = bank.getInstituteCode();
                }
            }

            return bankCode;
    }

    private void setFileList() throws Exception{
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
                    supportDataDto.setInstituteCode(getBankCode(fileEnum.getInstitute_name()));
                    supportDataDto.setSupportValue(Integer.valueOf(row.get(fileEnum.getCol())));
                    supportDataRepository.save(supportDataDto.toEntity());
                }
            }
        }
    }

    private void setBankList(){

        Bank bankA = new Bank();
        bankA.setInstituteName("주택도시기금");
        bankA.setInstituteCode("BNK093");
        bankDataRepository.save(bankA);
        Bank bankB = new Bank();
        bankB.setInstituteName("국민은행");
        bankB.setInstituteCode("BNK019");
        bankDataRepository.save(bankB);
        Bank bankC = new Bank();
        bankC.setInstituteName("우리은행");
        bankC.setInstituteCode("BNK020");
        bankDataRepository.save(bankC);
        Bank bankD = new Bank();
        bankD.setInstituteName("신한은행");
        bankD.setInstituteCode("BNK021");
        bankDataRepository.save(bankD);
        Bank bankF = new Bank();
        bankF.setInstituteName("한국시티은행");
        bankF.setInstituteCode("BNK027");
        bankDataRepository.save(bankF);
        Bank bankG = new Bank();
        bankG.setInstituteName("하나은행");
        bankG.setInstituteCode("BNK025");
        bankDataRepository.save(bankG);
        Bank bankH = new Bank();
        bankH.setInstituteName("농협은행/수협은행");
        bankH.setInstituteCode("BNK012");
        bankDataRepository.save(bankH);
        Bank bankI = new Bank();
        bankI.setInstituteName("외환은행");
        bankI.setInstituteCode("BNK005");
        bankDataRepository.save(bankI);
        Bank bankJ = new Bank();
        bankJ.setInstituteName("기타은행");
        bankJ.setInstituteCode("BNK051");
        bankDataRepository.save(bankJ);

    }

}
