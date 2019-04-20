package com.sung.housingfinance.entity;

import lombok.Data;

/*
 *
 * @author 123msn
 * @since 2019-04-03
 */
@Data
public class SupportAvg {

    private int year;
    private String instituteName;
    private Double supportAvg;

    public SupportAvg(int year, String instituteName, Double supportAvg){
        this.year = year;
        this.instituteName = instituteName;
        this.supportAvg = supportAvg;
    }

 }

