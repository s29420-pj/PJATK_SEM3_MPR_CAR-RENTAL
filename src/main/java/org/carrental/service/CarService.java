package org.carrental.service;

import lombok.RequiredArgsConstructor;
import org.carrental.exception.CarNotFoundException;
import org.carrental.exception.ValidationException;
import org.carrental.model.car.Car;
import org.carrental.model.car.CarStatus;
import org.carrental.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CarService {
    private final CarRepository carRepository;

    public Car createCar(Car car) {
        if (car.getMake() == null || car.getMake().isBlank()) {
            throw new ValidationException("cannot be blank", "make");
        }
        if (car.getVin().length() != 3) {
            throw new ValidationException("length must be 3", "vin");
        }

        carRepository.create(car);

        return car;
    }

    public Car getById(Integer id){
        Optional<Car> car = carRepository.getById(id);

        return car.orElseThrow(() ->  new CarNotFoundException("Car does not exist"));
    }

    public List<Car> getAvailableCars(){
        return carRepository.getByStatus(CarStatus.AVAILABLE);
    }

    public Car updateModel(Integer id, String newModel){
        if (newModel == null || newModel.isBlank()){
            throw new ValidationException("cannot be blank", "newModel");
        }

        return carRepository.updateModel(id, newModel)
                .orElseThrow(() -> new CarNotFoundException("car does not exist"));
    }

    public List<Car> getAll(){
        return carRepository.getAll();
    }
}