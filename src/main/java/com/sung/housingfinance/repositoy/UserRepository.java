package com.sung.housingfinance.repositoy;

import com.sung.housingfinance.entity.User;
import org.springframework.data.repository.CrudRepository;


/*
 *
 * @author 123msn
 * @since 2019-04-04
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
}
