package org.carrental.repository;

import org.carrental.model.rent.Rent;

import java.util.ArrayList;
import java.util.List;

public class RentRepository {
    List<Rent> rentList = new ArrayList<>();

    public Rent create(Rent rent){
        rent.setId(rentList.size());
        rentList.add(rent);

        return rent;
    }
}
