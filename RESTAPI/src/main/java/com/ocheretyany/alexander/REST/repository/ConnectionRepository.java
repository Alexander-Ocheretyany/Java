package com.ocheretyany.alexander.REST.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.ocheretyany.alexander.REST.model.Record;

@Repository
public interface ConnectionRepository extends JpaRepository<Record, Long> {
}
