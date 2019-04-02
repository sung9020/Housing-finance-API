package com.sung.housingfinance.dto;/*
 *
 * @author 123msn
 * @since 2019-04-02
 */

import com.sung.housingfinance.entity.SupportData;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class SupportDataDto {

    private int year;
    private int month;
    private String instituteName;
    private String instituteCode;
    private int supportValue;

    public SupportDataDto(){}

    public SupportDataDto(SupportData entity){
        this.year = entity.getYear();
        this.month = entity.getMonth();
        this.instituteName = entity.getInstituteName();
        this.instituteCode = entity.getInstituteCode();
        this.supportValue = entity.getSupportValue();
    }

    public SupportData toEntity(){
        SupportData supportData = new SupportData();
        supportData.setYear(year);
        supportData.setMonth(month);
        supportData.setInstituteName(instituteName);
        supportData.setInstituteCode(instituteCode);
        supportData.setSupportValue(supportValue);

        return supportData;
    }

}
