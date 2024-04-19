package com.aldoj.orca_plus_api.controllers;

import com.aldoj.orca_plus_api.configs.SecurityFilter;
import com.aldoj.orca_plus_api.configs.TokenService;
import com.aldoj.orca_plus_api.domain.budgets.GetBudgetDTO;
import com.aldoj.orca_plus_api.domain.budgets.PostBudgetDTO;
import com.aldoj.orca_plus_api.domain.user.User;
import com.aldoj.orca_plus_api.repositories.UserRepository;
import com.aldoj.orca_plus_api.services.GenerateBudgetService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("budget")
public class BudgetController {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenerateBudgetService generateBudgetService;

    @PostMapping
    public ResponseEntity create(@RequestBody PostBudgetDTO data, HttpServletRequest request) {
        var token = securityFilter.recoverToken(request);
        if (token == null) return ResponseEntity.badRequest().build();
        var email = tokenService.validateToken(token);
        var user = userRepository.findByEmail(email);
        generateBudgetService.generateBudget(data, (User) user);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<GetBudgetDTO>> get(HttpServletRequest request) {
        var token = securityFilter.recoverToken(request);
        if (token == null) return ResponseEntity.badRequest().build();
        var email = tokenService.validateToken(token);
        var user = (User) userRepository.findByEmail(email);
        var response = generateBudgetService.getAllBudgets(user.getId());
        return ResponseEntity.ok(response);
    }
}
