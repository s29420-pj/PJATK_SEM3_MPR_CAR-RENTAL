package org.carrental.model.rent;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
@RequiredArgsConstructor
@Data
public class RentRequest {
    private final Integer carId;
    private final Integer clientId;
    private final LocalDate startDate;
    private final int rentLength;
}
