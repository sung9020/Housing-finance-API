package com.sung.housingfinance.entity;

import lombok.Data;

import javax.persistence.Entity;

/*
 *
 * @author 123msn
 * @since 2019-04-03
 */
@Data
public class SupportAvg {

    private int year;
    private String instituteName;
    private String instituteCode;
    private Double supportAvg;

    public SupportAvg(int year, String instituteName, String instituteCode, Double supportAvg){
        this.year = year;
        this.instituteName = instituteName;
        this.instituteCode = instituteCode;
        this.supportAvg = supportAvg;
    }

 }

