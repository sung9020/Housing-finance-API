package com.sung.housingfinance.constants;
/*
 *
 * @author 123msn
 * @since 2019-04-01
 */


import lombok.Getter;

public enum FileEnum {

    YEAR(0, "연도", "NONE"),
    MONTH(1, "월",  "NONE"),
    MOLIT(2, "주택도시기금", "BANK093" ),
    KB(3, "국민은행", "BNK019"),
    WB(4, "우리은행", "BNK020"),
    SB(5, "신한은행", "BNK021"),
    CITI(6,"한국시티은행","BNK027"),
    HNB(7,"하나은행", "BNK025"),
    NH_SH(8, "농협은행/수협은행", "BNK012"),
    KEB(9,"외환은행","BNK005"),
    ETC(10,"기타은행","BNK051");

    @Getter
    private final int col;

    @Getter
    private final String institute_name;

    @Getter
    private final String institute_code;

    FileEnum(int col, String institute_name, String institute_code){
        this.col = col;
        this.institute_name = institute_name;
        this.institute_code = institute_code;

    }

}
