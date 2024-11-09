package edu.net.movierental.service.price.impls;

import edu.net.movierental.model.Price;
import edu.net.movierental.repository.PriceRepository;
import edu.net.movierental.service.price.IPriceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class PriceServiceImpl implements IPriceService {
    private final PriceRepository repository;

    @Override
    public Price save(Price price) {
        if (price.getId() != null) {
            return null;
        }
        return repository.save(price);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Price findById(Long id) {
        return repository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Price update(Price price) {
        Price existingPrice = repository.findById(price.getId()).orElseThrow(NoSuchElementException::new);
        existingPrice.setGenre(price.getGenre());
        existingPrice.setRentWithoutPenalty(price.getRentWithoutPenalty());
        existingPrice.setPriceWithoutPenalty(price.getPriceWithoutPenalty());
        existingPrice.setPriceforPenalDays(price.getPriceforPenalDays());
        return repository.save(existingPrice);
    }

    @Override
    public List<Price> findAll() {
        return repository.findAll();
    }
}
