package edu.net.movierental.service.customer.impls;

import edu.net.movierental.model.Customer;
import edu.net.movierental.repository.CustomerRepository;
import edu.net.movierental.service.customer.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.List;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements ICustomerService {
    private CustomerRepository repository;

    @Override
    public Customer save(Customer customer) {
        if(customer.getId() != null){
            return null;
        }
        return repository.save(customer);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Customer findById(Long id) {
        return repository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Customer update(Customer customer) {
        Customer existingCustomer = repository.findById(customer.getId()).orElseThrow(NoSuchElementException::new);
        existingCustomer.setName(customer.getName());
        return repository.save(existingCustomer);
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }
}
