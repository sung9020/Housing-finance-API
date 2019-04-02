package com.sung.housingfinance.repositoy;/*
 *
 * @author 123msn
 * @since 2019-04-02
 */

import com.sung.housingfinance.entity.Bank;
import org.springframework.data.repository.CrudRepository;

public interface BankDataRepository extends CrudRepository<Bank, Integer> {
}
