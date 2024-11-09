package edu.net.movierental.service.price;

import edu.net.movierental.model.Price;

import java.util.List;

public interface IPriceService {
    Price save(Price price);
    void delete(Long id);
    Price findById(Long id);
    Price update(Price price);
    List<Price> findAll();
}

