package org.carrental.repository;

import org.carrental.model.rent.Rent;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RentRepository {
    List<Rent> rentList = new ArrayList<>();

    public Rent create(Rent rent){
        rent.setId(rentList.size());
        rentList.add(rent);

        return rent;
    }
}