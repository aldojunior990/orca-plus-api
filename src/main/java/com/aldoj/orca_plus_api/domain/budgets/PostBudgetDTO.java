package com.aldoj.orca_plus_api.domain.budgets;

import java.util.List;
import java.util.UUID;

public record PostBudgetDTO(UUID clientId, List<BudgetItemDTO> items) {
}
