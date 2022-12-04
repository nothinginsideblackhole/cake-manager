package com.santosh.cakemanager.service;

import com.santosh.cakemanager.exception.CakeServiceException;
import com.santosh.cakemanager.model.Cake;
import com.santosh.cakemanager.repository.CakeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CakeServiceImplTest {

    @InjectMocks
    private CakeServiceImpl cakeService;

    @Mock
    private CakeRepository cakeRepository;

    @Test
    void getAllCakes() {
        when(cakeRepository.findAll()).thenReturn(List.of(new Cake(1L, "cake1", "CAKE1", "url1"), new Cake(1L, "cake2", "CAKE2", "url2")));
        List<Cake> allCakes = cakeService.getAllCakes();
        Assertions.assertEquals(allCakes.size(), 2);

        verify(cakeRepository, times(1)).findAll();
        verifyNoMoreInteractions(cakeRepository);
    }

    @Test
    void updateCake() throws CakeServiceException {
        Cake cake = new Cake(1L, "cake1", "CAKE1", "url1");
        when(cakeRepository.getReferenceById(1L)).thenReturn(cake);
        when(cakeRepository.save(cake)).thenReturn(cake);
        Cake cake1 = cakeService.updateCake(cake, 1L);
        Assertions.assertEquals(cake1, cake);


        verify(cakeRepository, times(1)).getReferenceById(1L);
        verify(cakeRepository, times(1)).save(cake);
        verifyNoMoreInteractions(cakeRepository);

    }

    @Test
    void deleteCake() throws CakeServiceException {
        Cake cake = new Cake(1L, "cake1", "CAKE1", "url1");
        doNothing().when(cakeRepository).delete(cake);
        when(cakeRepository.getCakeByTitle("cake1")).thenReturn(Optional.of(cake));
        cakeService.deleteCake(cake);

        verify(cakeRepository, times(1)).getCakeByTitle("cake1");
        verify(cakeRepository, times(1)).delete(cake);
        verifyNoMoreInteractions(cakeRepository);

    }

    @Test
    void addCake() throws CakeServiceException {
        Cake cake = new Cake(1L, "cake1", "CAKE1", "url1");
        when(cakeRepository.save(cake)).thenReturn(cake);
        Cake cake1 = cakeService.addCake(cake);
        Assertions.assertEquals(cake1, cake);

        verify(cakeRepository, times(1)).getCakeByTitle("cake1");
        verify(cakeRepository, times(1)).save(cake);
        verifyNoMoreInteractions(cakeRepository);
    }
}