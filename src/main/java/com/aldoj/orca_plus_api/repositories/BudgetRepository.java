package com.aldoj.orca_plus_api.repositories;

import com.aldoj.orca_plus_api.domain.budgets.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {

    public List<Budget> findAllByUserId(UUID userId);
}
