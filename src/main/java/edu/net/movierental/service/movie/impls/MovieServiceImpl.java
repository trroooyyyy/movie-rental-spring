package edu.net.movierental.service.movie.impls;

import edu.net.movierental.model.Movie;
import edu.net.movierental.repository.MovieRepository;
import edu.net.movierental.service.movie.IMovieService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements IMovieService {
    private final MovieRepository repository;

    @Override
    public Movie save(Movie movie) {
        if (movie.getId() != null) {
            return null;
        }
        return repository.save(movie);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Movie findById(Long id) {
        return repository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Movie update(Movie movie) {
        Movie existingMovie = repository.findById(movie.getId()).orElseThrow(NoSuchElementException::new);
        existingMovie.setName(movie.getName());
        existingMovie.setPrice(movie.getPrice());
        return repository.save(existingMovie);
    }

    @Override
    public List<Movie> findAll() {
        return repository.findAll();
    }
}
