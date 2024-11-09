package edu.net.movierental.controller.movie;

import edu.net.movierental.model.Movie;
import edu.net.movierental.service.movie.impls.MovieServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/movies")
@AllArgsConstructor
public class MovieController {
    private final MovieServiceImpl service;

    // Отримати всі фільми
    @GetMapping("/")
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = service.findAll();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    // Отримати фільм за ID
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") Long id) {
        try {
            Movie movie = service.findById(id);
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Зберегти новий фільм
    @PostMapping("/")
    public ResponseEntity<Movie> saveMovie(@Valid @RequestBody Movie movie) {
        Movie savedMovie = service.save(movie);
        if (savedMovie == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }

    // Оновити інформацію про фільм
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @Valid @RequestBody Movie movie) {
        try {
            movie.setId(id); // Встановлюємо ID для оновлення
            Movie updatedMovie = service.update(movie);
            return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Видалити фільм за ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMovie(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
