package edu.net.movierental.controller.rental;

import edu.net.movierental.model.Rental;
import edu.net.movierental.model.Movie;
import edu.net.movierental.model.Customer;
import edu.net.movierental.service.rental.impls.RentalServiceImpl;
import edu.net.movierental.service.movie.impls.MovieServiceImpl;
import edu.net.movierental.service.customer.impls.CustomerServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ui/rentals")
@AllArgsConstructor
public class RentalUIController {

    private final RentalServiceImpl rentalService;
    private final MovieServiceImpl movieService;
    private final CustomerServiceImpl customerService;

    @GetMapping("/")
    public String getAllRentals(Model model) {
        List<Rental> rentals = rentalService.findAll();
        List<Movie> movies = movieService.findAll();
        List<Customer> customers = customerService.findAll();

        model.addAttribute("rentals", rentals);
        model.addAttribute("movies", movies);
        model.addAttribute("customers", customers);
        model.addAttribute("rental", new Rental());
        return "rentals_list";
    }

    @PostMapping("/add")
    public String addRental(@ModelAttribute("rental") Rental rental) {
        rentalService.save(rental);
        return "redirect:/ui/rentals/";
    }

    @GetMapping("/edit/{id}")
    public String editRentalForm(@PathVariable("id") Long id, Model model) {
        Rental rental = rentalService.findById(id);
        List<Movie> movies = movieService.findAll();
        List<Customer> customers = customerService.findAll();

        model.addAttribute("rental", rental);
        model.addAttribute("movies", movies);
        model.addAttribute("customers", customers);
        return "edit_rental";
    }

    @PostMapping("/edit/{id}")
    public String editRental(@PathVariable("id") Long id, @ModelAttribute("rental") Rental rental) {
        rental.setId(id);
        rentalService.update(rental);
        return "redirect:/ui/rentals/";
    }

    @GetMapping("/delete/{id}")
    public String deleteRental(@PathVariable("id") Long id) {
        rentalService.delete(id);
        return "redirect:/ui/rentals/";
    }

    @GetMapping("/getStatement")
    public String getRentalStatement(@RequestParam("customerName") String customerName, Model model) {
        String statement = rentalService.getRentalStatementForCustomer(customerName);
        model.addAttribute("rentalStatement", statement);
        return "rental_statement";
    }
    @GetMapping("/toStatement")
    public String toRentalStatement() {
        return "rental_statement";
    }

}
