package com.sung.housingfinance.security;

/*
 *
 * @author 123msn
 * @since 2019-04-03
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sung.housingfinance.constants.ErrorEnum;
import com.sung.housingfinance.constants.RoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
@Slf4j
@PropertySource("classpath:application.yml")
public class JwtTokenProvider {

    @Value("${security.jwt.config.secretKey}")
    private String secretKey;

    @Value("${security.jwt.config.expireInMilliseconds}")
    private long expireInMilliseconds;

    @Autowired
    private CustomUserDetails customUserDetails;

    private final String AUTH_TOKEN_KEYWORD = "AUTH";

    private final String AUTH_MAP_KEYWORD = "authority";


    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(Authentication authentication){
        Claims claims = Jwts.claims();
        claims.put(AUTH_TOKEN_KEYWORD, AuthorityUtils.createAuthorityList(RoleEnum.ADMIN.getRole()));

        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expireInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(authentication.getName())
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .setId(String.valueOf(UUID.randomUUID()))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    private Claims getUserInfoByJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }

    public Authentication getAuthentication(String token){
        Claims claims = getUserInfoByJWT(token);
        UserDetails userDetails = customUserDetails.loadUserByUsername(claims.getSubject());

        if(claims.get(AUTH_TOKEN_KEYWORD) instanceof List){
            List<GrantedAuthority> grantedAuthorityList = getAuthListByToken((List) claims.get(AUTH_TOKEN_KEYWORD));
            return new UsernamePasswordAuthenticationToken(userDetails, "", grantedAuthorityList);
        }else{
            throw new IllegalArgumentException(ErrorEnum.FORBIDDEN_ERROR.getMsg());
        }
    }

    boolean vaildateToken(String token){

        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            log.error("에러메시지 :" + e.getMessage());
            log.error("jwt 토큰 미보유 유저");
            throw new IllegalArgumentException(ErrorEnum.TOKEN_ERROR.getMsg());
        }

    }

    private List<GrantedAuthority> getAuthListByToken(List tokenAuthorityList){

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        int tokenAuthorityListSize = tokenAuthorityList.size();
        for(int i = 0; i < tokenAuthorityListSize; i++){
            if(tokenAuthorityList.get(i) instanceof Map){
                Map authMap = (Map) tokenAuthorityList.get(i);
                if(authMap.get("authority") instanceof String){
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(String.valueOf(authMap.get(AUTH_MAP_KEYWORD)));
                    grantedAuthorityList.add(grantedAuthority);
                }
            }
        }

        return grantedAuthorityList;
    }
}
