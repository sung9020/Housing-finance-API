package com.sung.housingfinance.security;

import com.sung.housingfinance.dto.request.RequestDataForUser;
import com.sung.housingfinance.dto.response.ResponseDataForUser;

import javax.servlet.http.HttpServletRequest;

/*
 *
 * @author 123msn
 * @since 2019-04-04
 */
public interface SecurityInterface {

    ResponseDataForUser signIn(RequestDataForUser requset);

    ResponseDataForUser signUp(RequestDataForUser requset);

    ResponseDataForUser refresh();

}
