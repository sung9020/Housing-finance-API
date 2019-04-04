package com.sung.housingfinance.dto.response;

import lombok.Data;

/*
 *
 * @author 123msn
 * @since 2019-04-04
 */
@Data
public class ResponseDataForUser extends ResponseData{
    String username;
    String token;
}
