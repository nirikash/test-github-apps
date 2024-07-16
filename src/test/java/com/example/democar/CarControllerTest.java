package com.example.democar;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Test
    public void should_createCar() throws Exception {
        Car savedCar = new Car("test");
        savedCar.setId(2L);

        when(carService.addCar(any(Car.class))).thenAnswer((Answer<Car>) invocationOnMock -> {
            Car car = (Car) invocationOnMock.getArgument(0);
            car.setId(1L);
            return car;
        });

        mockMvc.perform(MockMvcRequestBuilders.post("/cars")
                        .content((new ObjectMapper()).writeValueAsString(new Car("test")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(HttpProperties.Encoding.DEFAULT_CHARSET.name())
                        )
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("name").value("test"))
        .andExpect(MockMvcResultMatchers.jsonPath("id").value("1"));

        ArgumentCaptor<Car> captor = ArgumentCaptor.forClass(Car.class);
        verify(carService).addCar(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("test");

// Alternative without argument validation
//        verify(carService, times(1)).addCar(any(Car.class));

    }

    @Test
    public void shouldGetCar() throws Exception {
        Long carId = 1L;
        Car car = new Car();
        car.setId(1L);
        car.setName("Honda");
        when(carService.getCar(1L)).thenReturn(car);
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/" + carId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("Honda"));
    }
}
