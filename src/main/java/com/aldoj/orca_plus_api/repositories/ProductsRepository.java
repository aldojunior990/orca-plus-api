package com.aldoj.orca_plus_api.repositories;

import com.aldoj.orca_plus_api.domain.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ProductsRepository extends JpaRepository<Product, UUID> {

    List<Product> findAllByUserId(UUID user_id);

}
