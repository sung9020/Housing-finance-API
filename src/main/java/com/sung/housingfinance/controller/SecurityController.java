package com.sung.housingfinance.controller;

import com.sung.housingfinance.dto.request.RequestDataForUser;
import com.sung.housingfinance.dto.response.ResponseData;
import com.sung.housingfinance.dto.response.ResponseDataForUser;
import com.sung.housingfinance.security.SecurityInterface;
import com.sung.housingfinance.security.impl.SecurityService;
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
@RequestMapping("auth")
public class SecurityController {

    @Autowired
    SecurityInterface securityInterface;

    @PutMapping("refresh")
    public ResponseDataForUser refresh(){

        ResponseDataForUser response = securityInterface.refresh();

        return response;
    }

    @PostMapping("signUp")
    public ResponseDataForUser signup(@RequestBody RequestDataForUser request){

        ResponseDataForUser response = securityInterface.signUp(request);

        return response;
    }

    @PostMapping("signIn")
    public ResponseDataForUser signin(@RequestBody RequestDataForUser request){

        ResponseDataForUser response = securityInterface.signIn(request);

        return response;
    }
}
