package org.carrental.controller;

import lombok.RequiredArgsConstructor;
import org.carrental.exception.ValidationException;
import org.carrental.model.car.Car;
import org.carrental.service.CarService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping("/all")
    public ResponseEntity<List<Car>> getCarList() {
        List<Car> carList = carService.getAll();

        return ResponseEntity.ok(carList);
    }

    @GetMapping
    public ResponseEntity<Car> getCarByIdParam(@RequestParam(name="id") Integer id) {
        Car car = carService.getById(id);

        return ResponseEntity.ok(car);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarByPathVariable(@PathVariable Integer id) {
        Car car = carService.getById(id);

        return ResponseEntity.ok(car);
    }

    @PostMapping("/create")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        try {
            Car createdCar = carService.createCar(car);

            return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(createdCar);
        } catch (ValidationException validationException) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("{id}/update")
    public ResponseEntity<Car> updateCar(@PathVariable Integer id, @RequestBody Car car) {
        Car updatedCar = carService.updateModel(id, car.getModel());

        return ResponseEntity.ok(updatedCar);
    }
}