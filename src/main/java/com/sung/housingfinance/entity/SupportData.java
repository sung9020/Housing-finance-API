package com.sung.housingfinance.entity;/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Data
@Entity
@Table(indexes = {
        @Index(name = "IDX_YEAR_MONTH", columnList = "year, month")
})
public class SupportData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private int year;

    @Column
    private int month;

    @Column
    private String instituteName;

    @Column
    private int supportValue;
}
