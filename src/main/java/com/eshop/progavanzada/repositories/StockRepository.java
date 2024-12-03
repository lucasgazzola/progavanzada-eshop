package com.eshop.progavanzada.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.progavanzada.models.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer> {

}
