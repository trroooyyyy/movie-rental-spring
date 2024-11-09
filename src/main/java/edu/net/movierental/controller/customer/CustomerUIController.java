package edu.net.movierental.controller.customer;

import edu.net.movierental.model.Customer;
import edu.net.movierental.service.customer.impls.CustomerServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ui/customers")
@AllArgsConstructor
public class CustomerUIController {

    private final CustomerServiceImpl customerService;

    @GetMapping("/")
    public String getAllCustomers(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        model.addAttribute("customer", new Customer());
        return "customers_list";
    }

    @PostMapping("/add")
    public String addCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        return "redirect:/ui/customers/";
    }

    @GetMapping("/edit/{id}")
    public String editCustomerForm(@PathVariable("id") Long id, Model model) {
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);
        return "edit_customer";
    }

    @PostMapping("/edit/{id}")
    public String editCustomer(@PathVariable("id") Long id, @ModelAttribute("customer") Customer customer) {
        customer.setId(id);
        customerService.update(customer);
        return "redirect:/ui/customers/";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        customerService.delete(id);
        return "redirect:/ui/customers/";
    }
}
