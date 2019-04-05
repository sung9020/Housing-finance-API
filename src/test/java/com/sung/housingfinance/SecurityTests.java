package com.sung.housingfinance;

/*
 *
 * @author 123msn
 * @since 2019-04-03
 */

import com.sung.housingfinance.entity.User;
import com.sung.housingfinance.repositoy.UserRepository;
import com.sung.housingfinance.security.JwtTokenProvider;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityExistsException;
import java.util.Optional;

import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class SecurityTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    final String sampleUsername1 = "guest";
    final String samplePassword1 = "1234";

    final String sampleUsername2 = "admin";
    final String samplePassword2 = "1234";

    @Before
    public void setup(){
        User user = new User();
        if(!userRepository.existsByUsername(sampleUsername2)){
            user.setUsername(sampleUsername2);
            user.setPassword(passwordEncoder.encode(samplePassword2));
            userRepository.save(user);
        }
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void 가입_재시도_Tests() {
        User user = new User();
        String token = "";

        user.setUsername(sampleUsername1);
        user.setPassword(passwordEncoder.encode(samplePassword1));
        userRepository.save(user);

        boolean result = userRepository.existsByUsername(sampleUsername1);

        if(!userRepository.existsByUsername(sampleUsername1)){
            user.setUsername(sampleUsername1);
            user.setPassword(passwordEncoder.encode(samplePassword1));
            userRepository.save(user);

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sampleUsername1, samplePassword1));
            token = Optional.of(jwtTokenProvider.createToken(authentication)).orElseThrow(() -> new EntityExistsException(""));
        }

        assertThat(token, Matchers.is(""));
    }

    @Test
    public void 가입_Tests(){
        User user = new User();
        String token = "";

        if(!userRepository.existsByUsername(sampleUsername1)){
            user.setUsername(sampleUsername1);
            user.setPassword(passwordEncoder.encode(samplePassword1));
            userRepository.save(user);

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sampleUsername1, samplePassword1));
            token = Optional.of(jwtTokenProvider.createToken(authentication)).orElseThrow(() -> new EntityExistsException(""));
        }

        assertThat(token, Matchers.not(""));
    }

    @Test
    public void 로그인_Tests() {
        String token = "";
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sampleUsername2, samplePassword2));
        token = Optional.of(jwtTokenProvider.createToken(authentication)).orElseThrow(() -> new EntityExistsException(""));

        assertThat(token, Matchers.not(""));
    }

    @Test
    public void 토큰갱신_Tests() {
        String token1 = "";
        Authentication authentication = new TestingAuthenticationToken(sampleUsername2, samplePassword2);
        token1 = Optional.of(jwtTokenProvider.createToken(authentication)).orElseThrow(() -> new EntityExistsException(""));

        authentication = jwtTokenProvider.getAuthentication(token1);
        String token2 = Optional.of(jwtTokenProvider.createToken(authentication)).orElseThrow(() -> new EntityExistsException("Not found UserInfo"));

        assertThat(token1, Matchers.not(token2));
    }
}
