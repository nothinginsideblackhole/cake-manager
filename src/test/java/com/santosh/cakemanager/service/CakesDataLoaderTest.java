package com.santosh.cakemanager.service;

import com.santosh.cakemanager.model.Cake;
import com.santosh.cakemanager.repository.CakeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CakesDataLoaderTest {

    @InjectMocks
    private CakesDataLoader cakesDataLoader;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CakeRepository cakeRepository;

    @Test
    void init() {
        ReflectionTestUtils.setField(cakesDataLoader, "cakesEndpoint", "http://url");
        URI url = URI.create("http://url");
        when(restTemplate.getForEntity(url, String.class)).thenReturn(new ResponseEntity<>("[{\"title\":\"cake1\",\"desc\":\"CAKE1\",\"image\":\"url1\"},{\"title\":\"cake2\",\"desc\":\"CAKE2\",\"image\":\"url2\"}]", HttpStatus.OK));
        cakesDataLoader.init();
        verify(restTemplate, times(1)).getForEntity(url, String.class);
        Set<Cake> cakes = Set.of(new Cake("cake1", "CAKE1", "url1"), new Cake("cake2", "CAKE2", "url2"));
        verify(cakeRepository, times(1)).saveAll(cakes);
        verifyNoMoreInteractions(restTemplate);
        verifyNoMoreInteractions(cakeRepository);
    }
}