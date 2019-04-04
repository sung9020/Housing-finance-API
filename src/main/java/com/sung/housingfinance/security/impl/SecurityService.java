package com.sung.housingfinance.security.impl;

import com.sung.housingfinance.constants.ErrorEnum;
import com.sung.housingfinance.dto.request.RequestDataForUser;
import com.sung.housingfinance.dto.response.ResponseData;
import com.sung.housingfinance.dto.response.ResponseDataForUser;
import com.sung.housingfinance.entity.User;
import com.sung.housingfinance.repositoy.UserRepository;
import com.sung.housingfinance.security.JwtTokenProvider;
import com.sung.housingfinance.security.SecurityInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.Optional;

/*
 *
 * @author 123msn
 * @since 2019-04-04
 */
@Service
public class SecurityService implements SecurityInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseDataForUser signIn(RequestDataForUser request) {

        ResponseDataForUser response = new ResponseDataForUser();

        String token = "";
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        token = Optional.of(jwtTokenProvider.createToken(authentication)).orElseThrow(() -> new EntityExistsException("Not found Entity"));

        response.setErrorCode(ErrorEnum.SUCCESS.getErrorCode());
        response.setMsg(ErrorEnum.SUCCESS.getMsg());
        response.setUsername(request.getUsername());
        response.setToken(token);

        return response;
    }

    @Override
    public ResponseDataForUser signUp(RequestDataForUser request) {

        User user = new User();
        ResponseDataForUser response = new ResponseDataForUser();
        String token = "";
        if(!userRepository.existsByUsername(request.getUsername())){
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            token = Optional.of(jwtTokenProvider.createToken(authentication)).orElseThrow(() -> new EntityExistsException("Not found Entity"));

            response.setErrorCode(ErrorEnum.SUCCESS.getErrorCode());
            response.setMsg(ErrorEnum.SUCCESS.getMsg());
            response.setUsername(request.getUsername());
            response.setToken(token);
        }else{
            response.setErrorCode(ErrorEnum.ALREADY_REGISTERED_USER_ERROR.getErrorCode());
            response.setMsg(ErrorEnum.ALREADY_REGISTERED_USER_ERROR.getMsg());
            response.setUsername(request.getUsername());
            response.setToken("");
        }


        return response;
    }

    @Override
    public ResponseDataForUser refresh() {
        ResponseDataForUser response = new ResponseDataForUser();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = Optional.of(jwtTokenProvider.createToken(authentication)).orElseThrow(() -> new EntityExistsException("Not found UserInfo"));

        response.setErrorCode(ErrorEnum.SUCCESS.getErrorCode());
        response.setMsg(ErrorEnum.SUCCESS.getMsg());
        response.setToken(token);

        return response;
    }
}
