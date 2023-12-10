package org.carrental;

import org.carrental.model.car.Car;
import org.carrental.model.car.CarClass;
import org.carrental.model.car.CarStatus;
import org.carrental.repository.CarRepository;
import org.carrental.service.CarService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        CarRepository carRepository = context.getBean("carRepository", CarRepository.class);
        CarService carService = context.getBean("carService", CarService.class);


//        CarRepository carRepository = new CarRepository();
//        CarService carService = new CarService(carRepository);


        Car car2 = new Car(null, "volkswagen", "passat",
                "abc",
                CarClass.STANDARD, CarStatus.RENTED, 50D);
        Car car3 = new Car(null, "volkswagen", "passat",
                "zxc",
                CarClass.PREMIUM, CarStatus.AVAILABLE, 30D);

//        Car createdCar = carService.createCar(car2);
        Car createdCar = carService.createCar(car2);
        carService.createCar(car3);

        System.out.println(createdCar);

        List<Car> carList = carService.getAll();
        System.out.println(carList);
//
//        Car carById = carService.getById(0);
//
//        System.out.println(carById);
//        carService.updateModel(2, "polo");
//
//        List<Car> availableCars = carService.getAvailableCars();
//
//        carRepository.removeAll();
//        System.out.println(availableCars);
//
//
//        Car invalidCar = new Car(null, "volkswagen",
//                "golf", "1234",
//                CarClass.STANDARD, CarStatus.AVAILABLE);
//
////        carService.createCar(invalidCar);


        // Plan działania
        // 1. Zaimplementowanie pozostałych metod z carRepository
        // 2. Utworzenie modelu,serwisu i repozytorium dla klienta
        // 3. Dodanie logów do CarService i ClientService wykorzystując np. log4j
        //
        // Kolejny zjazd:
        // Pokrycie projektu testami jednostkowymi i początek prac nad systemem rezerwacji

    }
}