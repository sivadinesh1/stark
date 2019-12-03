package com.squapl.stark.repository;


import com.squapl.stark.model.TrainerDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerDetailsRepository extends CrudRepository<TrainerDetails, Long> {


}

