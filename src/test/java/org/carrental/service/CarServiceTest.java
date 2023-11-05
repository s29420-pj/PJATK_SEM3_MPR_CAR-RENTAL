package org.carrental.service;

import org.carrental.exception.CarNotFoundException;
import org.carrental.exception.ValidationException;
import org.carrental.model.car.Car;
import org.carrental.model.car.CarClass;
import org.carrental.model.car.CarStatus;
import org.carrental.repository.CarRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {
    private static CarService carService;
    private static CarRepository carRepository;

    @BeforeAll
    static void setup() {
        carRepository = new CarRepository();
        carService = new CarService(carRepository);
    }


    @AfterEach
    void cleanUp() {
        carRepository.removeAll();
    }

    @Test
    void shouldCorrectlyCreateCar() {
        Car car = new Car(null, "volkswagen", "golf", "123",
                CarClass.STANDARD, CarStatus.AVAILABLE);

        Car result = assertDoesNotThrow(() -> carService.createCar(car));

        assertEquals(car.getMake(), result.getMake());
        assertNotNull(result.getId());
    }

    @Test
    void shouldNotReturnAnyCar() {
        List<Car> result = carService.getAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldNotCreateCarDueToValidation() {
        Car car = new Car(null, "volkswagen", "golf", "1234",
                CarClass.STANDARD, CarStatus.AVAILABLE);

        ValidationException result = assertThrows(ValidationException.class,
                () -> carService.createCar(car));

        assertEquals("vin", result.getField());
        assertEquals("vin length must be 3", result.getMessage());
    }

    @Test
    void shouldThrowCarNotFoundExceptionWhenIdDoesNotExist() {
        assertThrows(CarNotFoundException.class,
                () -> carService.getById(0));
    }

    @ParameterizedTest
    @MethodSource(value = "providesInvalidMakeValues")
    void shouldCorrectlyValidateMake(String make) {
        Car car = new Car(null, make,
                "golf", "123",
                CarClass.STANDARD,
                CarStatus.AVAILABLE);

        ValidationException result =
                assertThrows(ValidationException.class,
                        () -> carService.createCar(car));

        assertEquals("make", result.getField());
        assertEquals("make cannot be blank", result.getMessage());
    }

    @Test
    void shouldCorrectlyUpdateCar() {
        Car car = new Car(null, "volkswagen", "golf", "123",
                CarClass.STANDARD, CarStatus.AVAILABLE);

        Car createdCar = carService.createCar(car);

        Car result = carService.updateModel(createdCar.getId(), "passat");

        assertEquals("passat", result.getModel());
        assertNotEquals("golf", result.getModel());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingCarThatDoesNotExist() {
        assertThrows(CarNotFoundException.class,
                () -> carService.updateModel(1, "X"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void shouldThrowValidationExceptionWhenUpdatingCarWithInvalidModel(String newModel) {
        assertThrows(ValidationException.class, () -> carService.updateModel(1, newModel));
    }

    @Test
    void shouldReturnEmptyListOfAvailableCars(){
        List<Car> result = assertDoesNotThrow(() -> carService.getAvailableCars());

        assertTrue(result.isEmpty());
    }

    @Test
    void ShouldReturnAvailableCars(){
        Car car = new Car(null, "volkswagen", "golf", "123",
                CarClass.STANDARD, CarStatus.AVAILABLE);
        Car car2 = new Car(null, "volkswagen", "passat", "123",
                CarClass.STANDARD, CarStatus.RENTED);

        carService.createCar(car);
        carService.createCar(car2);

        List<Car> result = assertDoesNotThrow(() -> carService.getAvailableCars());

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertTrue(result
                .stream()
                .allMatch( it -> it.getStatus().equals(CarStatus.AVAILABLE)));
    }

    public static Stream<Arguments> providesInvalidMakeValues() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("  "),
                null
        );
    }

}