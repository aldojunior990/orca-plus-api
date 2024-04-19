package com.aldoj.orca_plus_api.repositories;

import com.aldoj.orca_plus_api.domain.budgets.BudgetItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BudgetItemRepository extends JpaRepository<BudgetItem, UUID> {
    List<BudgetItem> findAllByBudgetId(UUID budget_id);
}
