package com.sung.housingfinance.constants;

import lombok.Getter;

/*
 *
 * @author 123msn
 * @since 2019-04-04
 */
public enum RoleEnum {

    ADMIN("ROLE_ADMIN");

    @Getter
    private final String role;

    RoleEnum(String role){
        this.role = role;
    }
}
