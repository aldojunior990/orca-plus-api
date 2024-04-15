package com.aldoj.orca_plus_api.controllers;

import com.aldoj.orca_plus_api.configs.SecurityFilter;
import com.aldoj.orca_plus_api.configs.TokenService;
import com.aldoj.orca_plus_api.domain.client.Client;
import com.aldoj.orca_plus_api.domain.client.GetClientDTO;
import com.aldoj.orca_plus_api.domain.client.PostClientDTO;
import com.aldoj.orca_plus_api.domain.products.GetProductDTO;
import com.aldoj.orca_plus_api.domain.products.PostProductDTO;
import com.aldoj.orca_plus_api.domain.products.Product;
import com.aldoj.orca_plus_api.domain.user.User;
import com.aldoj.orca_plus_api.repositories.ClientsRepository;
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
@RequestMapping("client")
public class ClientController {
    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody PostClientDTO data, HttpServletRequest request) {
        var token = securityFilter.recoverToken(request);
        if (token == null) return ResponseEntity.badRequest().build();
        var email = tokenService.validateToken(token);
        var user = userRepository.findByEmail(email);
        clientsRepository.save(new Client(data, (User) user));
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<GetClientDTO>> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        var clients = clientsRepository.findAllByUserId(user.getId()).stream().map(it -> new GetClientDTO(it.getId(), it.getName(), it.getAddress(), it.getContact())).toList();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("{clientId}")
    public ResponseEntity<GetClientDTO> getById(@PathVariable UUID clientId) {
        var client = clientsRepository.findById(clientId);
        return ResponseEntity.ok(new GetClientDTO(client.get().getId(), client.get().getName(), client.get().getAddress(), client.get().getContact()));
    }

    @DeleteMapping("{clientId}")
    public ResponseEntity delete(@PathVariable UUID clientId) {
        clientsRepository.deleteById(clientId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{productId}")
    public ResponseEntity put(@PathVariable UUID clientId, @RequestBody PostClientDTO clientDTO) {
        var client = clientsRepository.findById(clientId);
        if (!clientDTO.name().isEmpty()) client.get().setName(clientDTO.name());
        if (!clientDTO.address().isEmpty()) client.get().setAddress(clientDTO.address());
        if (!clientDTO.contact().isEmpty()) client.get().setAddress(clientDTO.contact());
        clientsRepository.save(client.get());
        return ResponseEntity.ok().build();
    }
}
