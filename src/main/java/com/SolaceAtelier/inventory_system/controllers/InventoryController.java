package com.SolaceAtelier.inventory_system.controllers;

import com.SolaceAtelier.inventory_system.models.Product;
import com.SolaceAtelier.inventory_system.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // 3. LOCATIONS PAGE
    @GetMapping("/locations")
    public String locations() {
        return "locations";
    }

    // 4. LIST VIEW (ADMIN, STAFF, CUSTOMER)
    @GetMapping("/inventory")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'CUSTOMER')")
    public String viewInventory(@RequestParam(required = false) String brand,
                                @RequestParam(defaultValue = "name") String sortField,
                                Model model) {
        Sort sort = Sort.by(sortField).ascending();
        List<Product> products;

        if (brand != null && !brand.isEmpty()) {
            products = productRepo.findByBrandContainingAndNameContaining(brand, "", sort);
        } else {
            products = productRepo.findAll(sort);
        }

        model.addAttribute("products", products);
        return "inventory-list";
    }

    // 5. ADD FORM (ADMIN, STAFF only)
    @GetMapping("/inventory/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    // 6. SAVE FORM (ADMIN, STAFF only)
    @PostMapping("/inventory/save")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public String saveProduct(@Valid @ModelAttribute Product product, BindingResult result) {
        if (result.hasErrors()) {
            return "add-product";
        }
        productRepo.save(product);
        return "redirect:/inventory";
    }
}