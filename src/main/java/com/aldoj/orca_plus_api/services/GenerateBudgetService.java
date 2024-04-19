package com.aldoj.orca_plus_api.services;

import com.aldoj.orca_plus_api.domain.budgets.*;
import com.aldoj.orca_plus_api.domain.user.User;
import com.aldoj.orca_plus_api.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GenerateBudgetService {

    @Autowired
    private BudgetItemRepository budgetItemRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private UserRepository userRepository;

    public void generateBudget(PostBudgetDTO data, User user) {
        var client = clientsRepository.findById(data.clientId());
        var budget = new Budget(client.get(), user);
        budgetRepository.save(budget);

        var items = data.items();

        items.forEach(it -> {
            var product = productsRepository.findById(it.productId());
            budgetItemRepository.save(new BudgetItem(it.amount(), budget, product.get()));
        });
    }

    public List<GetBudgetDTO> getAllBudgets(UUID userId) {
        var userBudgets = budgetRepository.findAllByUserId(userId);
        List<GetBudgetDTO> budgets = new ArrayList<>();
        userBudgets.forEach(it -> {
            var items = budgetItemRepository.findAllByBudgetId(it.getId());
            var itemsDto = items.stream().map(item -> {
                var product = productsRepository.findById(item.getProduct().getId());
                return new BudgetItemDTO(item.getProduct().getId(), product.get().getName(), item.getAmount());
            }).toList();

            var user = userRepository.findById(userId).get();
            var client = clientsRepository.findById(it.getClient().getId());
            budgets.add(new GetBudgetDTO(user.getName(), user.getContact(), user.getAddress(), client.get().getName(), client.get().getContact(), client.get().getAddress(), it.getDate(), itemsDto));

        });
        return budgets;
    }
}
