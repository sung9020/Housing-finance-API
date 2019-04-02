package com.sung.housingfinance.entity;

import lombok.Data;

import java.time.Year;
/*
 *
 * @author 123msn
 * @since 2019-04-03
 */

@Data
public class SupportSum {

    private int year;
    private String instituteName;
    private String instituteCode;
    private long supportSum;

    public SupportSum(int year, String instituteName, String instituteCode, long supportSum){
        this.year = year;
        this.instituteName = instituteName;
        this.instituteCode = instituteCode;
        this.supportSum = supportSum;
    }
}
