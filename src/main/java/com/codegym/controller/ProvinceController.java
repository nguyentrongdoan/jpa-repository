package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.model.Province;
import com.codegym.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;

    @GetMapping("/create-province")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/province/create");
        modelAndView.addObject("province", new Province());
        return modelAndView;
    }
    @PostMapping("/create-province")
    public ModelAndView saveProvince(@ModelAttribute("province") Province province){
        provinceService.save(province);
        ModelAndView modelAndView = new ModelAndView("/province/create");
        modelAndView.addObject("province", new Province());
        modelAndView.addObject("message", "New province create successfully");
        return modelAndView;
    }

    @GetMapping("/provinces")
    public ModelAndView listProvinces(){
        Iterable<Province> provinces = provinceService.findAll();
        ModelAndView modelAndView = new ModelAndView("/province/list");
        modelAndView.addObject("provinces", provinces);
        return modelAndView;
    }

    // cap nhat province
    @GetMapping("/edit-province/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Province province = provinceService.findById(id);
        ModelAndView modelAndView;
        if (province != null){
            modelAndView = new ModelAndView("/province/edit");
            modelAndView.addObject("province", province);
        }else {
            modelAndView = new ModelAndView("/error.404");
        }
        return modelAndView;
    }
    @PostMapping("/edit-province")
    public ModelAndView updateCustomer(@ModelAttribute("province")Province province){
        provinceService.save(province);
        ModelAndView modelAndView = new ModelAndView("/province/edit");
        modelAndView.addObject("province", province);
        modelAndView.addObject("message", "Province update successfully");
        return modelAndView;
    }

    //xoa
    @GetMapping("/delete-province/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Province province = provinceService.findById(id);
        ModelAndView modelAndView;
        if (province != null){
            modelAndView = new ModelAndView("/province/delete");
            modelAndView.addObject("province", province);
        }else {
            modelAndView = new ModelAndView("/error.404");
        }
        return modelAndView;
    }
    @PostMapping("/delete-province")
    public String deleteProvince(@ModelAttribute("province")Province province){
        provinceService.remove(province.getId());
        return "redirect:provinces";
    }
}
