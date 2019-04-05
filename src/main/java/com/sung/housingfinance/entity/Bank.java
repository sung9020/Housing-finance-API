package com.sung.housingfinance.entity;/*
 *
 * @author 123msn
 * @since 2019-04-02
 */

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Bank {

    @Id
    @Column(unique = true)
    private String instituteName;

    @Column(unique = true)
    private String instituteCode;


}
