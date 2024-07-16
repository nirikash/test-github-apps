package com.example.democar;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    public static final long CAR_ID = 2L;
    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @Test
    public void test_successfullyAddCar() {
        Car car = new Car("test");
        when(carRepository.save(any(Car.class)))
                .thenAnswer((Answer<Car>) invocationOnMock -> {
                    Car savedCar = invocationOnMock.getArgument(0);
                    savedCar.setId(CAR_ID);
                    return savedCar;
                });

        Car savedCar = carService.addCar(car);

        Assertions.assertThat(savedCar).isNotNull();
        assertThat(savedCar.getId()).isEqualTo(CAR_ID);
        verify(carRepository).save(car);
    }

}
