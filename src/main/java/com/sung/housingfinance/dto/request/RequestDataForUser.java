package com.sung.housingfinance.dto.request;

import lombok.Data;

/*
 *
 * @author 123msn
 * @since 2019-04-04
 */
@Data
public class RequestDataForUser {

    private String username;

    private String password;

    private String token;
}
