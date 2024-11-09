package edu.net.movierental.service.movie;

import edu.net.movierental.model.Movie;

import java.util.List;

public interface IMovieService {
    Movie save(Movie movie);
    void delete(Long id);
    Movie findById(Long id);
    Movie update(Movie movie);
    List<Movie> findAll();
}
