package com.sung.housingfinance;/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

import com.sung.housingfinance.dto.BankDataDto;
import com.sung.housingfinance.entity.Bank;
import com.sung.housingfinance.entity.SupportData;
import com.sung.housingfinance.repositoy.BankDataRepository;
import com.sung.housingfinance.repositoy.SupportDataRepository;
import org.assertj.core.util.Streams;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles(profiles = "test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RepositoryTests {

    @Autowired
    private SupportDataRepository supportDataRepository;

    @Autowired
    private BankDataRepository bankDataRepository;

    @Test
    public void getSupportDataTest(){
        SupportData supportDataA = new SupportData();
        supportDataA.setYear(2014);
        supportDataA.setMonth(3);
        supportDataA.setInstituteName("주택도시기금");
        supportDataA.setInstituteCode("BNK093");
        supportDataA.setSupportValue(2422);

        supportDataRepository.save(supportDataA);
        SupportData supportData = supportDataRepository.findFirstByInstituteName("주택도시기금");

        assertThat(supportData, Matchers.notNullValue());
        assertThat(supportData.getInstituteName(), Matchers.is("주택도시기금"));
        }

    @Test
    public void getBankListTest(){
        Bank bankA = new Bank();
        bankA.setInstituteName("주택도시기금");
        bankA.setInstituteCode("BNK093");

        Bank bankB = new Bank();
        bankB.setInstituteName("국민은행");
        bankB.setInstituteCode("BNK019");

        bankDataRepository.save(bankA);
        bankDataRepository.save(bankB);

        Iterable<Bank> bankDataIterable = bankDataRepository.findAll();
        List<BankDataDto> bankList = Streams.stream(bankDataIterable).map(BankDataDto::new).collect(Collectors.toList());

        assertThat(bankList, Matchers.notNullValue());
        assertThat(bankList, Matchers.hasSize(2));
    }

}
