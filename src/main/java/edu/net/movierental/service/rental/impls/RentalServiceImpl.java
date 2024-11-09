package edu.net.movierental.service.rental.impls;

import edu.net.movierental.model.Price;
import edu.net.movierental.model.Rental;
import edu.net.movierental.model.Movie;
import edu.net.movierental.model.Customer;
import edu.net.movierental.repository.RentalRepository;
import edu.net.movierental.service.rental.IRentalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class RentalServiceImpl implements IRentalService {
    private final RentalRepository repository;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public String getRentalStatementForCustomer(String customerName) {
        List<Rental> rentals = repository.findAll();
        double totalCost = 0;
        StringBuilder statement = new StringBuilder();

        Optional<Customer> customerOpt = rentals.stream()
                .map(Rental::getCustomer)
                .filter(c -> c.getName().equalsIgnoreCase(customerName))
                .findFirst();

        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            statement.append("Rental statement for ").append(customer.getName()).append(":\n");

            for (Rental rental : rentals) {
                if (rental.getCustomer().equals(customer)) {
                    if (rental.getRentalFinish() != null) {
                        Movie movie = rental.getMovie();
                        double movieCost = calculateRentalCost(rental);
                        totalCost += movieCost;

                        String rentalStartDate = DATE_FORMAT.format(rental.getRentalStart());
                        String rentalFinishDate = DATE_FORMAT.format(rental.getRentalFinish());

                        statement.append(String.format("%s has rented '%s' from %s to %s. Total cost: %.2f\n",
                                customer.getName(),
                                movie.getName(),
                                rentalStartDate,
                                rentalFinishDate,
                                movieCost));
                    } else {
                        statement.append(String.format("%s has rented '%s' but not yet returned.\n",
                                customer.getName(),
                                rental.getMovie().getName()));
                    }
                }
            }

            statement.append("Total cost for all returned rentals: ").append(totalCost).append("\n");
        } else {
            statement.append("Customer not found.");
        }

        return statement.toString();
    }

    private double calculateRentalCost(Rental rental) {
        double totalCost = 0;
        Movie movie = rental.getMovie();
        if (movie != null && movie.getPrice() != null) {
            if (rental.getRentalStart() != null && rental.getRentalFinish() != null) {
                long duration = rental.getRentalFinish().getTime() - rental.getRentalStart().getTime();
                long daysRented = TimeUnit.MILLISECONDS.toDays(duration); // total rental days

                Price price = movie.getPrice();

                // Rent cost without penalty applies immediately
                totalCost += price.getRentWithoutPenalty();

                // Add cost per day for each rental day without penalty, up to 7 days
                long daysWithoutPenalty = Math.min(daysRented, 7);
                totalCost += daysWithoutPenalty * price.getPriceWithoutPenalty();

                // Add penalty for each day beyond 7 days
                if (daysRented > 7) {
                    long penaltyDays = daysRented - 7;
                    totalCost += penaltyDays * price.getPriceforPenalDays();
                }
            }
        }
        return totalCost;
    }




    @Override
    public Rental save(Rental rental) {
        if (rental.getId() != null) {
            return null;
        }
        return repository.save(rental);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Rental findById(Long id) {
        return repository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Rental update(Rental rental) {
        Rental existingRental = repository.findById(rental.getId()).orElseThrow(NoSuchElementException::new);
        existingRental.setMovie(rental.getMovie());
        existingRental.setCustomer(rental.getCustomer());
        existingRental.setRentalStart(rental.getRentalStart());
        existingRental.setRentalFinish(rental.getRentalFinish());
        return repository.save(existingRental);
    }

    @Override
    public List<Rental> findAll() {
        return repository.findAll();
    }
}
