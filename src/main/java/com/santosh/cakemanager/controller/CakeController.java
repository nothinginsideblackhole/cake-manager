package com.santosh.cakemanager.controller;

import com.santosh.cakemanager.exception.CakeServiceException;
import com.santosh.cakemanager.model.Cake;
import com.santosh.cakemanager.service.CakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/cakes")
public class CakeController {

    @Autowired
    private CakeService cakeService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Cake>> getAll() {
        final List<Cake> allCakes = cakeService.getAllCakes();
        return new ResponseEntity<>(allCakes, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Cake> create(@RequestBody Cake cake) {
        try {
            final Cake createdCake = cakeService.addCake(cake);
            return new ResponseEntity<>(createdCake, HttpStatus.CREATED);
        } catch (CakeServiceException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseEntity<Cake> update(@RequestBody Cake cake, @PathVariable Long id) {
        try {
            final Cake updatedCake = cakeService.updateCake(cake,id);
            return new ResponseEntity<>(updatedCake, HttpStatus.CREATED);
        } catch (CakeServiceException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Cake> delete(@RequestBody Cake cake) {
        try {
            cakeService.deleteCake(cake);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CakeServiceException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
