package com.example.democar;

import org.springframework.stereotype.Component;

@Component
public class CarService {
    final private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car addCar(Car car) {
        Car savedCar = carRepository.save(car);
        return savedCar;
    }

    public Car getCar(Long id) {
        return carRepository.findById(id).get();
    }
}
