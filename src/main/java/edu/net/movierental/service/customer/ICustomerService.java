package edu.net.movierental.service.customer;

import edu.net.movierental.model.Customer;

import java.util.List;

public interface ICustomerService {
    Customer save(Customer customer);
    void delete(Long id);
    Customer findById(Long id);
    Customer update(Customer customer);
    List<Customer> findAll();
}
