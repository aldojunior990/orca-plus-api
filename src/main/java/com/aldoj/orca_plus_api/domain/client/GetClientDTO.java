package com.aldoj.orca_plus_api.domain.client;

import java.util.UUID;

public record GetClientDTO(UUID id, String name, String address, String contact) {
}
