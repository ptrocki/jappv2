package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.InsuranceOption;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InsuranceOption entity.
 */
@SuppressWarnings("unused")
public interface InsuranceOptionRepository extends JpaRepository<InsuranceOption,Long> {

}
