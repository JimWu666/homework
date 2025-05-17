package com.CathayJavaTest.persistence.repository;

import com.CathayJavaTest.persistence.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    Optional<Currency> findByCode(String code);
}
