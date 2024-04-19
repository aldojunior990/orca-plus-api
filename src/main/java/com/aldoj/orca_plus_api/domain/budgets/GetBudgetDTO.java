package com.aldoj.orca_plus_api.domain.budgets;

import java.util.List;

public record GetBudgetDTO(String userName, String userContact, String userAddress, String clientName,
                           String clientContact, String clientAddress, String date, List<BudgetItemDTO> products) {
}
