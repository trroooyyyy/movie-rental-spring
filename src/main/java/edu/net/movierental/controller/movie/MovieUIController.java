package edu.net.movierental.controller.movie;

import edu.net.movierental.model.Movie;
import edu.net.movierental.model.Price;
import edu.net.movierental.service.movie.impls.MovieServiceImpl;
import edu.net.movierental.service.price.impls.PriceServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ui/movies")
@AllArgsConstructor
public class MovieUIController {

    private final MovieServiceImpl movieService;
    private final PriceServiceImpl priceService;

    @GetMapping("/")
    public String getAllMovies(Model model) {
        List<Movie> movies = movieService.findAll();
        List<Price> prices = priceService.findAll();
        model.addAttribute("movies", movies);
        model.addAttribute("prices", prices);
        model.addAttribute("movie", new Movie());
        return "movies_list";
    }

    @PostMapping("/add")
    public String addMovie(@ModelAttribute("movie") Movie movie, @RequestParam("price.id") Long priceId) {
        Price price = priceService.findById(priceId);
        movie.setPrice(price);
        movieService.save(movie);
        return "redirect:/ui/movies/";
    }

    @GetMapping("/edit/{id}")
    public String editMovieForm(@PathVariable("id") Long id, Model model) {
        Movie movie = movieService.findById(id);
        List<Price> prices = priceService.findAll();
        model.addAttribute("movie", movie);
        model.addAttribute("prices", prices);
        return "edit_movie";
    }

    @PostMapping("/edit/{id}")
    public String editMovie(@PathVariable("id") Long id, @ModelAttribute("movie") Movie movie, @RequestParam("price.id") Long priceId) {
        Price price = priceService.findById(priceId);
        movie.setId(id);
        movie.setPrice(price);
        movieService.update(movie);
        return "redirect:/ui/movies/";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable("id") Long id) {
        movieService.delete(id);
        return "redirect:/ui/movies/";
    }
}
