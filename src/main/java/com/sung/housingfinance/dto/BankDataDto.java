package com.sung.housingfinance.dto;
/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

import com.sung.housingfinance.entity.Bank;
import lombok.Data;

@Data
public class BankDataDto {
    private String institute_name;
    private String institute_code;

    public BankDataDto(){

    }

    public BankDataDto(Bank entity){
        this.institute_name = entity.getInstituteName();
        this.institute_code = entity.getInstituteCode();
    }
}
