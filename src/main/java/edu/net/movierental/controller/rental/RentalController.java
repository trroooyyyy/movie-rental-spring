package edu.net.movierental.controller.rental;

import edu.net.movierental.model.Rental;
import edu.net.movierental.service.rental.impls.RentalServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/rentals")
@AllArgsConstructor
public class RentalController {
    private final RentalServiceImpl service;

    // Get all rental records
    @GetMapping("/")
    public ResponseEntity<List<Rental>> getAllRentals() {
        List<Rental> rentals = service.findAll();
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    // Get rental by ID
    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable("id") Long id) {
        try {
            Rental rental = service.findById(id);
            return new ResponseEntity<>(rental, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Save a new rental
    @PostMapping("/")
    public ResponseEntity<Rental> saveRental(@Valid @RequestBody Rental rental) {
        Rental savedRental = service.save(rental);
        if (savedRental == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedRental, HttpStatus.CREATED);
    }

    // Update rental information
    @PutMapping("/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable Long id, @Valid @RequestBody Rental rental) {
        try {
            rental.setId(id); // Set ID for update
            Rental updatedRental = service.update(rental);
            return new ResponseEntity<>(updatedRental, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete rental by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRental(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
