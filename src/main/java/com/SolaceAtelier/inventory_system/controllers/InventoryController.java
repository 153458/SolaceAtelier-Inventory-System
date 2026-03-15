package com.SolaceAtelier.inventory_system.controllers;

import com.SolaceAtelier.inventory_system.models.Product;
import com.SolaceAtelier.inventory_system.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class InventoryController {

    @Autowired
    private ProductRepository productRepo;

    // 1. HOME PAGE
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // 2. ABOUT PAGE
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    // 3. LOCATIONS PAGE (3rd Informational Page)
    @GetMapping("/locations")
    public String locations() {
        return "locations";
    }

    // 4. LIST VIEW (With Search & Sort)
    @GetMapping("/inventory")
    public String viewInventory(@RequestParam(required = false) String brand, 
                                @RequestParam(defaultValue = "name") String sortField,
                                Model model) {
        Sort sort = Sort.by(sortField).ascending();
        List<Product> products;
        
        if (brand != null && !brand.isEmpty()) {
            // Filtering logic
            products = productRepo.findByBrandContainingAndNameContaining(brand, "", sort);
        } else {
            products = productRepo.findAll(sort);
        }
        
        model.addAttribute("products", products);
        return "inventory-list";
    }

    // 5. THE FORM (Add Product)
    @GetMapping("/inventory/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    // 6. FORM SUBMISSION (With Validation)
    @PostMapping("/inventory/save")
    public String saveProduct(@Valid @ModelAttribute Product product, BindingResult result) {
        if (result.hasErrors()) {
            return "add-product"; // Stay on page if there are validation errors
        }
        productRepo.save(product);
        return "redirect:/inventory"; // Redirect after success
    }
}