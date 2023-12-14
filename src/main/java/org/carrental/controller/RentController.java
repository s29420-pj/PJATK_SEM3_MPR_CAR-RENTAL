package org.carrental.controller;

import lombok.RequiredArgsConstructor;
import org.carrental.exception.ValidationException;
import org.carrental.model.car.Car;
import org.carrental.model.rent.Rent;
import org.carrental.model.rent.RentRequest;
import org.carrental.service.CarService;
import org.carrental.service.RentService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/rent")
@RequiredArgsConstructor
public class RentController {
    private final RentService rentService;

    @PostMapping("/create")
    public ResponseEntity<Rent> create(@RequestBody RentRequest rentRequest) {
        try {
            Rent createdRent = rentService.create(rentRequest.getCarId(), rentRequest.getClientId(), rentRequest.getStartDate(), rentRequest.getRentLength());

            return ResponseEntity
                    .status(HttpStatusCode.valueOf(201)).body(createdRent);
        } catch (ValidationException validationException) {
            return ResponseEntity.badRequest().build();
        }
    }

}
