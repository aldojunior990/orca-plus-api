package com.aldoj.orca_plus_api.domain.budgets;

import java.util.UUID;

public record BudgetItemDTO(UUID productId, String productName, Integer amount) {

}
