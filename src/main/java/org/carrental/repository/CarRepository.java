package org.carrental.repository;

import org.carrental.model.car.Car;
import org.carrental.model.car.CarStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CarRepository {
    List<Car> carList = new ArrayList<>();

    public Car create(Car car) {
        car.setId(carList.size());
        carList.add(car);

        return car;
    }

    public Optional<Car> getById(Integer id){
        return carList.stream()
                .filter(car -> car.getId().equals(id))
                .findFirst();
    }

    public List<Car> getByStatus(CarStatus carStatus){
        return carList.stream()
                .filter(car -> car.getStatus().equals(carStatus))
                .collect(Collectors.toList());
    }

    public List<Car> getAll(){
        return carList;
    }

    public void removeAll() {
        carList = new ArrayList<>();
    }

    public void removeById(Integer id){
        Optional<Car> optionalCar = getById(id);

        optionalCar.ifPresent(it -> carList.remove(it));
    }

    public Optional<Car> updateModel(Integer id, String newModel){
        Optional<Car> optionalCar = getById(id);

        return optionalCar.map( car -> {
            car.setModel(newModel);
            return car;
        });
    }

}