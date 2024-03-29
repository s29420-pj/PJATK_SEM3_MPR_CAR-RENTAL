package org.carrental.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.carrental.model.car.Car;
import org.carrental.model.car.CarClass;
import org.carrental.model.car.CarStatus;
import org.carrental.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CarControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void cleanup(){
        carRepository.removeAll();
    }

    @Test
    void shouldCreateNewCar() throws JsonProcessingException {
        Car car = new Car(null, "volkswagen", "passat",
                "abc",
                CarClass.STANDARD, CarStatus.RENTED, 50D);

        String carJson = objectMapper.writeValueAsString(car);

        Car result = webTestClient.post().uri("/car/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(carJson)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Car.class)
                .returnResult().getResponseBody();

        assertEquals(result.getVin(), car.getVin());
        assertEquals(result, carRepository.getById(result.getId()).get());
    }


    @Test
    void shouldReturnCarByParam(){
        Car car = new Car(null, "volkswagen", "passat",
                "abc",
                CarClass.STANDARD, CarStatus.RENTED, 50D);
        carRepository.create(car);

        Car result = webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/car")
                                .queryParam("id", 0)
                                .build())
                .exchange()
                .expectBody(Car.class)
                .returnResult().getResponseBody();


        assertEquals(result.getVin(), car.getVin());
        assertEquals(result, carRepository.getById(result.getId()).get());
    }

    @Test
    void shouldReturnAllCars(){
        Car car = new Car(null, "volkswagen", "passat",
                "abc",
                CarClass.STANDARD, CarStatus.RENTED, 50D);
        Car car2 = new Car(null, "volkswagen", "polo",
                "abc",
                CarClass.STANDARD, CarStatus.RENTED, 25D);
        carRepository.create(car);
        carRepository.create(car2);

        List<Car> result = webTestClient.get().uri("/car/all")
                .exchange()
                .expectBodyList(Car.class)
                .returnResult().getResponseBody();


        assertEquals(2, result.size());
        assertEquals(result, carRepository.getAll());
    }

    @Test
    void shouldReturnCarByPathVariable(){
        Car car = new Car(null, "volkswagen", "passat",
                "abc",
                CarClass.STANDARD, CarStatus.RENTED, 50D);
        carRepository.create(car);

        Car result = webTestClient.get().uri("/car/0")
                .exchange()
                .expectBody(Car.class)
                .returnResult().getResponseBody();


        assertEquals(result.getVin(), car.getVin());
        assertEquals(result, carRepository.getById(result.getId()).get());
    }

    @Test
    void shouldUpdateCar() throws JsonProcessingException {
        Car car = new Car(null, "volkswagen", "passat",
                "abc",
                CarClass.STANDARD, CarStatus.RENTED, 50D);
        carRepository.create(car);

        Car updatedCar = new Car(null, "volkswagen", "golf",
                "123",
                CarClass.STANDARD, CarStatus.RENTED, 50D);

        String carJson = objectMapper.writeValueAsString(updatedCar);

        Car result = webTestClient.put().uri("/car/0/update")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(carJson)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Car.class)
                .returnResult().getResponseBody();

        assertEquals(result.getModel(), updatedCar.getModel());
        assertNotEquals(result.getVin(), updatedCar.getVin());
    }
}