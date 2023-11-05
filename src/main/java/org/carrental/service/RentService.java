package org.carrental.service;

import lombok.RequiredArgsConstructor;
import org.carrental.exception.CarNotFoundException;
import org.carrental.exception.RentCreationException;
import org.carrental.exception.RentException;
import org.carrental.model.car.Car;
import org.carrental.model.rent.Rent;
import org.carrental.repository.RentRepository;

import java.time.LocalDate;

@RequiredArgsConstructor
public class RentService {
    private final CarService carService;
    private final RentRepository rentRepository;
    private final ClientService clientService;

    public Rent create(Integer carId,
                       Integer clientId,
                       LocalDate startDate,
                       int rentLength) {
        // Start date nie może być w przeszłości
        // carId != null
        // clientId != null
        // rentLength > 0
        // Czy pojazd jest dostępny


        Car selectedCar;
        try {
            selectedCar = carService.getById(carId);
        } catch (CarNotFoundException exception){
            throw new RentException("Cannot create rent", exception);
        }
        // To samo dla klienta

        // Aktualizacja pojazdu do statusu rented

        Double price = selectedCar.getDailyRate() * rentLength;
        // Sprawdzić testem czy cena jest prawidłowo naliczona
        LocalDate endDate = startDate.plusDays(rentLength);
        // Sprawdzić testem czy data końcowa się zgadza

        Rent rent = new Rent(carId, clientId, startDate, endDate, price);

        return rentRepository.create(rent);
    }

    // metoda na pobranie wszystkich nadchodzących rezerwacji

}
