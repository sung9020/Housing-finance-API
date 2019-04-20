package com.sung.housingfinance.dto;/*
 *
 * @author 123msn
 * @since 2019-04-02
 */

import com.sung.housingfinance.entity.SupportData;
import lombok.Data;

/**
 * 지원 금액 원본데이터 DTO
 */
@Data
public class SupportDataDto {

    private int year;
    private int month;
    private String instituteName;
    private int supportValue;

    public SupportDataDto(){}

    public SupportDataDto(SupportData entity){
        this.year = entity.getYear();
        this.month = entity.getMonth();
        this.instituteName = entity.getInstituteName();
        this.supportValue = entity.getSupportValue();
    }

    public SupportData toEntity(){
        SupportData supportData = new SupportData();
        supportData.setYear(year);
        supportData.setMonth(month);
        supportData.setInstituteName(instituteName);
        supportData.setSupportValue(supportValue);

        return supportData;
    }

}
