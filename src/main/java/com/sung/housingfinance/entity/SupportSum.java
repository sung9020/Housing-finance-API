package com.sung.housingfinance.entity;

import lombok.Data;
/*
 *
 * @author 123msn
 * @since 2019-04-03
 */

@Data
public class SupportSum {

    private int year;
    private String instituteName;
    private long sum;

    public SupportSum(int year, String instituteName, long sum){
        this.year = year;
        this.instituteName = instituteName;
        this.sum = sum;
    }
}
