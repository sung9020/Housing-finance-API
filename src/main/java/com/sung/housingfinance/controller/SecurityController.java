package com.sung.housingfinance.controller;

import com.sung.housingfinance.dto.request.RequestDataForUser;
import com.sung.housingfinance.dto.response.ResponseData;
import com.sung.housingfinance.dto.response.ResponseDataForUser;
import com.sung.housingfinance.security.SecurityInterface;
import com.sung.housingfinance.security.impl.SecurityService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/*
 *
 * @author 123msn
 * @since 2019-04-03
 */
@RestController
@RequestMapping("api/auth")
public class SecurityController {

    @Autowired
    SecurityInterface securityInterface;

    @PutMapping("refresh")
    @ApiOperation(value ="토큰 갱신 API")
    public ResponseDataForUser refresh(){

        ResponseDataForUser response = securityInterface.refresh();

        return response;
    }

    @PostMapping("signUp")
    @ApiOperation(value ="회원가입 및 토큰 발급 API")
    public ResponseDataForUser signup(
            @ApiParam(required = true, name="userInfo", value = "token 값 채울 필요없이 요청 가능")
            @RequestBody RequestDataForUser request){

        ResponseDataForUser response = securityInterface.signUp(request);

        return response;
    }

    @PostMapping("signIn")
    @ApiOperation(value ="로그인 API")
    public ResponseDataForUser signin(
            @ApiParam(required = true, name="userInfo", value = "token 값 채울 필요없이 요청 가능")
            @RequestBody RequestDataForUser request){

        ResponseDataForUser response = securityInterface.signIn(request);

        return response;
    }
}
