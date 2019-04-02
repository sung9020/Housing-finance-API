package com.sung.housingfinance.repositoy;
/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

import com.sung.housingfinance.entity.SupportAvg;
import com.sung.housingfinance.entity.SupportData;
import com.sung.housingfinance.entity.SupportSum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SupportDataRepository extends JpaRepository<SupportData, Long> {

    SupportData findFirstByInstituteName(String institute_name);

    @Query("select new com.sung.housingfinance.entity.SupportSum(m.year, m.instituteName, m.instituteCode, sum(m.supportValue)) from SupportData m Group by m.year, m.instituteName")
    List<SupportSum> findBySupportSum();

    @Query("select new com.sung.housingfinance.entity.SupportAvg(m.year, m.instituteName, m.instituteCode, avg(m.supportValue)) from SupportData m where m.instituteName =:instituteName Group by m.year, m.instituteName")
    List<SupportAvg> findBySupportAvg(@Param("instituteName") String instituteName);
}
