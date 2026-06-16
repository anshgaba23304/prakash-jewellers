package com.prakash.jewellers.repository;

import com.prakash.jewellers.model.Promise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromiseRepository extends JpaRepository<Promise, Long> {
    List<Promise> findAllByOrderBySortOrderAsc();
}
