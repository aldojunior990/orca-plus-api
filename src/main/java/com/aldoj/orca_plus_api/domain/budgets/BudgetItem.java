package com.aldoj.orca_plus_api.domain.budgets;

import com.aldoj.orca_plus_api.domain.products.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "budget_item")
@Table(name = "budget_item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BudgetItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private Integer amount;


    @ManyToOne
    @JoinColumn(name = "budget_id", nullable = false)
    private Budget budget;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public BudgetItem(Integer amount, Budget budget, Product product) {
        this.amount = amount;
        this.budget = budget;
        this.product = product;
    }
}
