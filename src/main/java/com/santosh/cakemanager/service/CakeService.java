package com.santosh.cakemanager.service;

import com.santosh.cakemanager.exception.CakeServiceException;
import com.santosh.cakemanager.model.Cake;
import com.santosh.cakemanager.model.request.CakeUpdateRequest;

import java.util.List;

public interface CakeService {

    /**
     * Get All Cakes.
     * @return Cakes
     */
    List<Cake> getAllCakes();


    /**
     * Update Cake.
     *
     * @param cakeUpdateRequest cakeUpdateRequest.
     * @return cake.
     */
    Cake updateCake(CakeUpdateRequest cakeUpdateRequest, String title) throws CakeServiceException;

    /**
     * Delete Cake.
     * @param cake cake.
     */
    void deleteCake(Cake cake) throws CakeServiceException;

    /**
     * Add Cake.
     *
     * @param cake cake.
     * @return cake.
     */
    Cake addCake(Cake cake) throws CakeServiceException;
}
