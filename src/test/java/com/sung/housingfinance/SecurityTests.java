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
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityExistsException;
import java.util.Optional;
import java.util.regex.Matcher;

import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "local")
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
        if(!userRepository.existsByUsername(sampleUsername1)){
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
    public void 가입_재시도_Tests() throws Exception{
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
    public void 가입_Tests() throws Exception{
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
    public void 로그인_Tests() throws Exception{
        String token = "";
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sampleUsername2, samplePassword2));
        token = Optional.of(jwtTokenProvider.createToken(authentication)).orElseThrow(() -> new EntityExistsException(""));

        assertThat(token, Matchers.not(""));
    }

    @Test
    public void 토큰갱신_Tests() throws Exception{

        String token = "";
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sampleUsername2, samplePassword2));
        token = Optional.of(jwtTokenProvider.createToken(authentication)).orElseThrow(() -> new EntityExistsException(""));

        String token1 = token; // getJwtByRequest() 대체
        authentication = jwtTokenProvider.getAuthentication(token1);
        String token2 = Optional.of(jwtTokenProvider.createToken(authentication)).orElseThrow(() -> new EntityExistsException("Not found UserInfo"));

        assertThat(token1, Matchers.not(token2));
    }
}
