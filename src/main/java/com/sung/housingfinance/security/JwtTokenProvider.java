package com.sung.housingfinance.security;

/*
 *
 * @author 123msn
 * @since 2019-04-03
 */

import com.sung.housingfinance.constants.RoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
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

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(Authentication authentication){
        Claims claims = Jwts.claims().setSubject(authentication.getName());
        claims.put("auth", AuthorityUtils.createAuthorityList(RoleEnum.ADMIN.getRole()));
        claims.setId(String.valueOf(UUID.randomUUID()));

        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expireInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String getUserInfoByJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = customUserDetails.loadUserByUsername(getUserInfoByJWT(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", AuthorityUtils.createAuthorityList(RoleEnum.ADMIN.getRole()));
    }



    public boolean vaildateToken(String token){

        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
           log.error("에러메시지 :" + e.getMessage());
           log.error("jwt 토큰 미보유 유저");
        }

        return false;
    }
}
