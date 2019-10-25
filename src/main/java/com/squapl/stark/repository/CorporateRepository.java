package com.squapl.stark.repository;


import com.squapl.stark.model.Corporate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CorporateRepository extends JpaRepository<Corporate, Long> {


    Corporate findByName(String name);

    List<Corporate> findByIsactiveOrderByNameAsc(String status);

    @Modifying
    @Query("update Corporate cd set cd.isactive = :status, cd.updatedby = :updatedby, cd.updateddatetime = :updateddatetime  where cd.id = :id")
    public int setStatusForCorporate(@Param("id") Long id, @Param("status") String status, @Param("updatedby") Long updatedby, @Param("updateddatetime") Date updateddatetime);


    Long countByIsactive(String status);

}