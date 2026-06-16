package com.prakash.jewellers.repository;

import com.prakash.jewellers.model.Reel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReelRepository extends JpaRepository<Reel, Long> {
    List<Reel> findAllByOrderBySortOrderAsc();
}
