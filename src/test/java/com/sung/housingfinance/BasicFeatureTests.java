package com.sung.housingfinance;

import com.sung.housingfinance.repositoy.SupportDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "local")
public class BasicFeatureTests {

    @InjectMocks
    private SupportDataRepository supportDataRepository;

    @Test
    public void readCsvTest(){

    }

    @Test
    public void getBankListTest(){

    }


    @Test
    public void getSupportStatusDataTest(){


    }

    @Test
    public void getMaxSupportSumTest(){


    }

    @Test
    public void getKEBMinMaxDataTest(){


    }

    @Test
    public void getPredictedSupportDataTest(){


    }

}
