package com.sung.housingfinance.constants;/*
 *
 * @author 123msn
 * @since 2019-04-01
 */


public enum FileEnum {

    YEAR(0, "연도"),
    MONTH(1, "월"),
    MOLIT(2, "주택도시기금"),
    KB(3, "국민은행"),
    WB(4, "우리은행"),
    SB(5, "신한은행"),
    CITI(6,"한국시티은행"),
    HNB(7,"하나은행"),
    NH_SH(8, "농협은행/수협은행"),
    KEB(9,"외환은행"),
    ETC(10,"기타은행");

    private final int col;
    private final String institute_name;

    FileEnum(int col, String institute_name){
        this.col = col;
        this.institute_name = institute_name;

    }

}
