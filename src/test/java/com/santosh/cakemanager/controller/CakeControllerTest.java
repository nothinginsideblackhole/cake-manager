package com.santosh.cakemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santosh.cakemanager.exception.CakeServiceException;
import com.santosh.cakemanager.model.Cake;
import com.santosh.cakemanager.model.request.CakeUpdateRequest;
import com.santosh.cakemanager.service.CakeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CakeController.class)
class CakeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CakeService cakeService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    objectMapper = new ObjectMapper();
    }

    @Test
    void getAll() throws Exception {
        when(cakeService.getAllCakes()).thenReturn(List.of(new Cake(1L,"cake1","CAKE1","url1"),new Cake(1L,"cake2","CAKE2","url2")));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/cakes")
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());
        verify(cakeService,times(1)).getAllCakes();
        verifyNoMoreInteractions(cakeService);
    }

    @Test
    void create() throws Exception{
        Cake cake = new Cake(null,"cake1","CAKE1","url1");
        when(cakeService.addCake(cake)).thenReturn(cake);
        String cakeString = objectMapper.writeValueAsString(cake);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/cakes")
                        .content(cakeString)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title",is("cake1")))
                .andExpect(jsonPath("$.desc",is("CAKE1")))
                .andExpect(jsonPath("$.image",is("url1")));
        verify(cakeService,times(1)).addCake(cake);
        verifyNoMoreInteractions(cakeService);
    }

    @Test
    void createException() throws Exception{
        Cake cake = new Cake(null,"cake1","CAKE1","url1");
        when(cakeService.addCake(cake)).thenThrow(new CakeServiceException("blah"));
        String cakeString = objectMapper.writeValueAsString(cake);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/cakes")
                        .content(cakeString)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(cakeService,times(1)).addCake(cake);
        verifyNoMoreInteractions(cakeService);
    }

    @Test
    void update() throws Exception {
        Cake cake = new Cake(null, "cake1", "CAKE1", "url1");
        CakeUpdateRequest cakeUpdateRequest = new CakeUpdateRequest("CAKE1", "url1");
        when(cakeService.updateCake(cakeUpdateRequest, "cake1")).thenReturn(cake);
        String updateRequest = objectMapper.writeValueAsString(cakeUpdateRequest);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/cakes/{title}", "cake1")
                        .content(updateRequest)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("cake1")))
                .andExpect(jsonPath("$.desc", is("CAKE1")))
                .andExpect(jsonPath("$.image", is("url1")));

        verify(cakeService, times(1)).updateCake(cakeUpdateRequest, "cake1");
        verifyNoMoreInteractions(cakeService);
    }

    @Test
    void updateException() throws Exception {
        CakeUpdateRequest cakeUpdateRequest = new CakeUpdateRequest("CAKE1", "url1");
        when(cakeService.updateCake(cakeUpdateRequest, "cake1")).thenThrow(new CakeServiceException("blah"));
        String updateRequest = objectMapper.writeValueAsString(cakeUpdateRequest);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/cakes/{title}", "cake1")
                        .content(updateRequest)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(cakeService, times(1)).updateCake(cakeUpdateRequest, "cake1");
        verifyNoMoreInteractions(cakeService);
    }

    @Test
    void delete() throws Exception{
        Cake cake = new Cake(null,"cake1","CAKE1","url1");
        String cakeString = objectMapper.writeValueAsString(cake);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/cakes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cakeString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        verify(cakeService,times(1)).deleteCake(cake);
        verifyNoMoreInteractions(cakeService);
    }

    @Test
    void deleteException() throws Exception{
        Cake cake = new Cake(null,"cake1","CAKE1","url1");
        doThrow(new CakeServiceException("Meh")).when(cakeService).deleteCake(cake);
        String cakeString = objectMapper.writeValueAsString(cake);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/cakes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cakeString)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(cakeService,times(1)).deleteCake(cake);
        verifyNoMoreInteractions(cakeService);
    }
}