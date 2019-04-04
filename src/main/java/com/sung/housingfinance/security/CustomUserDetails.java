package com.sung.housingfinance.security;

import com.sun.security.auth.UserPrincipal;
import com.sung.housingfinance.constants.RoleEnum;
import com.sung.housingfinance.entity.User;
import com.sung.housingfinance.repositoy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.Optional;

/*
 *
 * @author 123msn
 * @since 2019-04-04
 */
@Service
public class CustomUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = Optional.of(userRepository.findByUsername(username)).orElseThrow(() -> new UsernameNotFoundException(""));


        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .authorities(AuthorityUtils.createAuthorityList(RoleEnum.ADMIN.getRole()))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();

    }
}
