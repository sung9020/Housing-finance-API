package com.sung.housingfinance.constants;/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

public enum BankEnum {

    MOLIT("주택도시기금", "BANK093"),
    KB("국민으행", "BNK019"),
    WB("우리은행", "BNK020"),
    SB("신한은행", "BNK021"),
    CITI("한국시티은행","BNK027"),
    HNB("하나은행","BNK025"),
    NH_SH("농협은행/수협은행", "BNK012"),
    KEB("외환은행","BNK005"),
    ETC("기타은행","BNK051");

    private final String institute_name;
    private final String institute_code;

    BankEnum(String institute_name, String institute_code){
        this.institute_name = institute_name;
        this.institute_code = institute_code;

    }


}
