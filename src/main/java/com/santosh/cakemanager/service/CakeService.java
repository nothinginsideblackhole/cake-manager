package com.santosh.cakemanager.service;

import com.santosh.cakemanager.exception.CakeServiceException;
import com.santosh.cakemanager.model.Cake;

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
     * @param cake cake.
     * @return cake.
     */
    Cake updateCake(Cake cake,Long id) throws CakeServiceException;

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
