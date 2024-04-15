package com.aldoj.orca_plus_api.controllers;

import com.aldoj.orca_plus_api.configs.SecurityFilter;
import com.aldoj.orca_plus_api.configs.TokenService;
import com.aldoj.orca_plus_api.domain.products.GetProductDTO;
import com.aldoj.orca_plus_api.domain.products.Product;
import com.aldoj.orca_plus_api.domain.products.PostProductDTO;
import com.aldoj.orca_plus_api.domain.user.User;
import com.aldoj.orca_plus_api.repositories.ProductsRepository;
import com.aldoj.orca_plus_api.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody PostProductDTO data, HttpServletRequest request) {
        var token = securityFilter.recoverToken(request);
        if (token == null) return ResponseEntity.badRequest().build();
        var email = tokenService.validateToken(token);
        var user = userRepository.findByEmail(email);
        productsRepository.save(new Product(data, (User) user));
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<GetProductDTO>> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        var products = productsRepository.findAllByUserId(user.getId()).stream().map(it -> new GetProductDTO(it.getId(), it.getName(), it.getPrice())).toList();
        return ResponseEntity.ok(products);
    }

    @GetMapping("{productId}")
    public ResponseEntity<GetProductDTO> getById(@PathVariable UUID productId) {
        var product = productsRepository.findById(productId);
        return ResponseEntity.ok(new GetProductDTO(product.get().getId(), product.get().getName(), product.get().getPrice()));
    }

    @DeleteMapping("{productId}")
    public ResponseEntity delete(@PathVariable UUID productId) {
        productsRepository.deleteById(productId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{productId}")
    public ResponseEntity put(@PathVariable UUID productId, @RequestBody PostProductDTO productDTO) {
        var product = productsRepository.findById(productId);
        if (!productDTO.name().isEmpty()) product.get().setName(productDTO.name());
        if (productDTO.price() >= 0) product.get().setPrice(productDTO.price());
        productsRepository.save(product.get());
        return ResponseEntity.ok().build();
    }
}