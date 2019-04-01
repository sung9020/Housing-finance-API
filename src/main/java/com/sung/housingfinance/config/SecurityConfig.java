//package com.sung.housingfinance.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.header.writers.StaticHeadersWriter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    SpringSecurityService springSecurityService;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Autowired
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(springSecurityService).passwordEncoder(passwordEncoder());
//    }
//
//    /*ignore resources*/
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        super.configure(web);
//        web
//                .ignoring()
//                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
//    }
//
//
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/h2-console/**")
//                .permitAll()
//                .anyRequest().authenticated()
//                .antMatchers("/**")
//                .permitAll()
//                .anyRequest().authenticated()    //Adding this line solved it
//                .and()
//                .csrf()
//                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("!/h2-console/**"))
//                .and()
//                .headers()
//                .addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy", "script-src 'self'"))
//                .frameOptions().disable()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .defaultSuccessUrl("/main")
//                .failureUrl("/login?error=true")
//                .and()
//                .logout()
//                .permitAll()
//                .logoutSuccessUrl("/login");
//    }
//
//}
