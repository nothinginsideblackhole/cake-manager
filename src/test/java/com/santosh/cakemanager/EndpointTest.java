package com.santosh.cakemanager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.santosh.cakemanager.model.Cake;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class EndpointTest {

    @Test
    void name() throws URISyntaxException, JsonProcessingException {
        final RestTemplate restTemplate = new RestTemplate();
        final String baseUrl ="https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";
        final URI uri = new URI(baseUrl);
        final ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
        final List<Cake> myObjects = new ObjectMapper().readValue(responseEntity.getBody(), new TypeReference<>() {
            /**
             * @return type.
             */
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        System.out.println(myObjects);
    }
}
