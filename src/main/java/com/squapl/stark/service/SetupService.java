package com.squapl.stark.service;

import com.squapl.stark.model.*;
import com.squapl.stark.util.APIResponseObj;

import java.util.Date;
import java.util.List;

public interface SetupService {

    // corporates
    public APIResponseObj addCorporates(Corporate corporateVO);

    public List<Corporate> getAllCorporates(String status);

    public int getCorporatesCount(String status);

    public APIResponseObj updateCorporate(Corporate corporateVO);

    // centers
    public APIResponseObj addCenter(Center centerVO);

    public int getCentersCount(String status, Long corporate_id);

    public List<Center> getAllCenters(String status, Long corporate_id);

    public APIResponseObj updateCenter(Center centerVO);


    // trainsers
    public TrainerDetails addTrainer(TrainerDetails trainerDetails);

    // service category
    public List<ServiceCategory> getAllServiceCategories(String status, Long center_id);

    public List<ServiceSubCategory> getServiceSubCategory(Long category_id);

    public APIResponseObj addServiceCategory(ServiceCategory serviceCategoryVO);

    public APIResponseObj updateServiceCategory(ServiceCategory serviceCategory);


    // sscategory
    public List<ServiceSubCategory> getAllServiceSubCategories(String status, Long center_id);

    public APIResponseObj addServiceSubCategory(ServiceSubCategory serviceSubCategoryVO);

    public APIResponseObj updateSSCategory(ServiceSubCategory serviceSubCategory);


    // entity updates
    public APIResponseObj updateEntityStatus(String entity, Long id, String status, Long loggedinuserid, Date whenupdated);


}

