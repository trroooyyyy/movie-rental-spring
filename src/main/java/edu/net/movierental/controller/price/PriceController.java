package edu.net.movierental.controller.price;

import edu.net.movierental.model.Price;
import edu.net.movierental.service.price.impls.PriceServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/prices")
@AllArgsConstructor
public class PriceController {
    private final PriceServiceImpl service;

    // Отримати всі цінові записи
    @GetMapping("/")
    public ResponseEntity<List<Price>> getAllPrices() {
        List<Price> prices = service.findAll();
        return new ResponseEntity<>(prices, HttpStatus.OK);
    }

    // Отримати ціну за ID
    @GetMapping("/{id}")
    public ResponseEntity<Price> getPriceById(@PathVariable("id") Long id) {
        try {
            Price price = service.findById(id);
            return new ResponseEntity<>(price, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Зберегти нову ціну
    @PostMapping("/")
    public ResponseEntity<Price> savePrice(@Valid @RequestBody Price price) {
        Price savedPrice = service.save(price);
        if (savedPrice == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedPrice, HttpStatus.CREATED);
    }

    // Оновити інформацію про ціну
    @PutMapping("/{id}")
    public ResponseEntity<Price> updatePrice(@PathVariable Long id, @Valid @RequestBody Price price) {
        try {
            price.setId(id); // Встановлюємо ID для оновлення
            Price updatedPrice = service.update(price);
            return new ResponseEntity<>(updatedPrice, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Видалити ціну за ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePrice(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
