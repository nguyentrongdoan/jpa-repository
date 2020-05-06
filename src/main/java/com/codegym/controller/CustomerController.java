package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/create-customer")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }
    @PostMapping("/create-customer")
    public ModelAndView saveCustomer(@ModelAttribute("customer")Customer customer){
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("message", "New customer create successfully");
        return modelAndView;
    }

    @GetMapping("/customers")
    public ModelAndView listCustomers(){
//        List<Customer> customers = customerService.findAll();
        Iterable<Customer> customers = customerService.findAll();
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    // cap nhat customer
    @GetMapping("/edit-customer/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Customer customer = customerService.findById(id);
        ModelAndView modelAndView;
        if (customer != null){
            modelAndView = new ModelAndView("/customer/edit");
            modelAndView.addObject("customer", customer);
        }else {
            modelAndView = new ModelAndView("/error.404");
        }
        return modelAndView;
    }
    @PostMapping("/edit-customer")
    public ModelAndView updateCustomer(@ModelAttribute("customer")Customer customer){
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/customer/edit");
        modelAndView.addObject("customer", customer);
        modelAndView.addObject("message", "Customer update successfully");
        return modelAndView;
    }

    //xoa
    @GetMapping("/delete-customer/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Customer customer = customerService.findById(id);
        ModelAndView modelAndView;
        if (customer != null){
            modelAndView = new ModelAndView("/customer/delete");
            modelAndView.addObject("customer", customer);
        }else {
            modelAndView = new ModelAndView("/error.404");
        }
        return modelAndView;
    }
    @PostMapping("/delete-customer")
    public String deleteCustomer(@ModelAttribute("customer")Customer customer){
        customerService.remove(customer.getId());
        return "redirect:customers";
    }
}
