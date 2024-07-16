package com.example.democar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Enumeration;

@RestController
public class CarController {
    private final CarService carService;
    @Autowired
    HttpServletRequest request;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping(value= "/cars", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Car createCar(@RequestBody Car car) {
        return carService.addCar(car);
    }

    @GetMapping(value= "/cars/{id}")
    public Car getCar(@PathVariable Long id) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            System.out.println(headerNames.nextElement());
        }
        return carService.getCar(id);
    }
}
