package com.aldoj.orca_plus_api.domain.products;

import java.util.UUID;

public record GetProductDTO(UUID id, String name, Double price) {
}
