package edu.net.movierental.service.rental;

import edu.net.movierental.model.Rental;

import java.util.List;

public interface IRentalService {
    Rental save(Rental rental);
    void delete(Long id);
    Rental findById(Long id);
    Rental update(Rental rental);
    List<Rental> findAll();
}
