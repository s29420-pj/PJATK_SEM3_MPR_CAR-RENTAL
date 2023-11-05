package org.carrental.model.rent;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Rent {
    private Integer id;
    private final Integer carId;
    private final Integer clientId;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Double price;
}
