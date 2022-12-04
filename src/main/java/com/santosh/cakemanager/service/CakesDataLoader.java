package com.santosh.cakemanager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.santosh.cakemanager.model.Cake;
import com.santosh.cakemanager.repository.CakeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;

@Component
@Slf4j
public class CakesDataLoader {

    @Value("${cakes.endpoint}")
    private String cakesEndpoint;

    @Autowired
    private CakeRepository cakeRepository;

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    void init() {
        try {
            log.info("Init...Loading Cakes...");
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
            cakeRepository.saveAll(new HashSet<>(cakes));
        } catch (URISyntaxException | JsonProcessingException e) {
            throw new RuntimeException("Unable to cake data from json source");
        }
    }
}
