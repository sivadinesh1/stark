package com.squapl.stark.repository;


import com.squapl.stark.model.Center;
import com.squapl.stark.model.Corporate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CenterRepository extends JpaRepository<Center, Long>, CenterRepositoryCustom {


    Center findByName(String name);

    List<Center> findByIsactiveOrderByNameAsc(String status);

    List<Center> findByIsactiveAndCorporateOrderByNameAsc(String status, Corporate corporate);

    Long countByIsactiveAndCorporate(String status, Corporate corporate);

    @Modifying
    @Query("update Center cd set cd.isactive = :status, cd.lastModifiedBy = :updatedby, cd.lastModifiedDateTime = :updateddatetime  where cd.id = :id")
    public int setStatusForCenter(@Param("id") Long id, @Param("status") String status, @Param("updatedby") String updatedby, @Param("updateddatetime") Date updateddatetime);

}