package com.sung.housingfinance.entity;/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

import lombok.Data;
import org.springframework.stereotype.Indexed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

@Data
@Entity
@Table(indexes = {
        @Index(name = "IDX_YEAR_MONTH", columnList = "year, month")
})
public class SupportDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private int year;

    @Column
    private int month;

    @Column
    private String institute_name;

    @Column
    private String institute_code;

    @Column
    private int support_value;

}
