package com.sung.housingfinance;/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

import com.sung.housingfinance.repositoy.SupportDataRepository;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


import org.junit.Test;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "local")
public class RepositoryTests {

    @InjectMocks
    private SupportDataRepository supportDataRepository;

    @Test
    public void saveSupportDataTest(){

    }

    @Test
    public void getSupportDataTest(){

    }
}
