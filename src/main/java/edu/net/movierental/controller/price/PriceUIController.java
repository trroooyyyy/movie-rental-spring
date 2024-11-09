package edu.net.movierental.controller.price;

import edu.net.movierental.model.Price;
import edu.net.movierental.service.price.impls.PriceServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ui/prices")
@AllArgsConstructor
public class PriceUIController {

    private final PriceServiceImpl priceService;

    @GetMapping("/")
    public String getAllPrices(Model model) {
        List<Price> prices = priceService.findAll();
        model.addAttribute("prices", prices);
        model.addAttribute("price", new Price());
        return "prices_list";
    }

    @PostMapping("/add")
    public String addPrice(@ModelAttribute("price") Price price) {
        priceService.save(price);
        return "redirect:/ui/prices/";
    }

    @GetMapping("/edit/{id}")
    public String editPriceForm(@PathVariable("id") Long id, Model model) {
        Price price = priceService.findById(id);
        model.addAttribute("price", price);
        return "edit_price";
    }

    @PostMapping("/edit/{id}")
    public String editPrice(@PathVariable("id") Long id, @ModelAttribute("price") Price price) {
        price.setId(id);
        priceService.update(price);
        return "redirect:/ui/prices/";
    }

    @GetMapping("/delete/{id}")
    public String deletePrice(@PathVariable("id") Long id) {
        priceService.delete(id);
        return "redirect:/ui/prices/";
    }
}
