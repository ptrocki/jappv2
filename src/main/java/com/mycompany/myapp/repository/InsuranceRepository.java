package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Insurance;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Insurance entity.
 */
@SuppressWarnings("unused")
public interface InsuranceRepository extends JpaRepository<Insurance,Long> {
    List<Insurance> findByNameContaining(String name);
}
