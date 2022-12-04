package com.santosh.cakemanager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.santosh.cakemanager.exception.CakeServiceException;
import com.santosh.cakemanager.model.Cake;
import com.santosh.cakemanager.repository.CakeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CakeServiceImpl implements CakeService {

    @Value("${cakes.endpoint}")
    private String cakesEndpoint;

    @Autowired
    private CakeRepository cakeRepository;

    @PostConstruct
    void init() {
        try {
            log.info("Init...Loading Cakes...");
            final RestTemplate restTemplate = new RestTemplate();
            final URI uri = new URI(cakesEndpoint);
            final ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
            final ObjectMapper objectMapper = new ObjectMapper();
            final List<Cake> cakes = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            });
            //Eliminating Duplicates
            saveAll(new HashSet<>(cakes));
        } catch (URISyntaxException | JsonProcessingException e) {
            throw new RuntimeException("Unable to cake data from json source");
        }
    }

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
     * @param cake cake.
     * @return cake
     */
    @Override
    public Cake updateCake(final Cake cake,final Long id) throws CakeServiceException {
        final Cake dbCake = cakeRepository.getReferenceById(id);
        dbCake.setDescription(cake.getDescription());
        dbCake.setImageLink(cake.getImageLink());
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

    private void saveAll(final Collection<Cake> cakes) {
        log.info("Save All size: " + cakes.size());
        cakeRepository.saveAll(cakes);
    }

    private void cakeExists(final String title) throws  CakeServiceException{
       if(getCakeByTitle(title).isPresent()){
            final String message = "Cake already exists with title : " + title;
            log.error(message);
            throw new CakeServiceException(message);
        }
    }

    private Cake validateCake(final String title) throws CakeServiceException {
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
