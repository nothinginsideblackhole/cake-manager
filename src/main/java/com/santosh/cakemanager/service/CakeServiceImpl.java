package com.santosh.cakemanager.service;

import com.santosh.cakemanager.exception.CakeServiceException;
import com.santosh.cakemanager.model.Cake;
import com.santosh.cakemanager.model.request.CakeUpdateRequest;
import com.santosh.cakemanager.repository.CakeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CakeServiceImpl implements CakeService {


    @Autowired
    private CakeRepository cakeRepository;


    /**
     * Get All Cakes.
     *
     * @return Cakes
     */
    @Override
    public List<Cake> getAllCakes() {
        log.info("getAllCakes");
        return cakeRepository.findAll();
    }


    /**
     * Update Cake.
     *
     * @param cakeUpdateRequest cakeUpdateRequest.
     * @return cake
     */
    @Override
    public Cake updateCake(final CakeUpdateRequest cakeUpdateRequest, final String title) throws CakeServiceException {
        Cake dbCake = validateCake(title);
        dbCake.setDescription(cakeUpdateRequest.getDescription());
        dbCake.setImageLink(cakeUpdateRequest.getImageLink());
        return cakeRepository.save(dbCake);
    }

    /**
     * Delete Cake.
     *
     * @param cake cake.
     */
    @Override
    public void deleteCake(final Cake cake) throws CakeServiceException {
        final Cake dbCake = validateCake(cake.getTitle());
        cakeRepository.delete(dbCake);
    }

    /**
     * Add Cake.
     *
     * @param cake cake.
     * @return cake
     */
    @Override
    public Cake addCake(final Cake cake) throws CakeServiceException {
        cakeExists(cake.getTitle());
        return cakeRepository.save(cake);
    }

    private void cakeExists(final String title) throws  CakeServiceException{
       if(getCakeByTitle(title).isPresent()){
            final String message = "Cake already exists with title : " + title;
            log.error(message);
            throw new CakeServiceException(message);
        }
    }

    public Cake validateCake(final String title) throws CakeServiceException {
        final Optional<Cake> cakeByTitle = getCakeByTitle(title);
        final String message = "No cake found with the title : " + title;
        return cakeByTitle.orElseThrow(() -> new CakeServiceException(message));
    }

    /**
     * Get Cake By Title
     *
     * @param title title
     * @return cake.
     */
    private Optional<Cake> getCakeByTitle(final String title) {
        log.info("getCakeByTitle : " + title);
        return cakeRepository.getCakeByTitle(title);
    }
}
