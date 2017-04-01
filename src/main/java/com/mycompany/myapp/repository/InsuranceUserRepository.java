package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.InsuranceUser;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InsuranceUser entity.
 */
@SuppressWarnings("unused")
public interface InsuranceUserRepository extends JpaRepository<InsuranceUser,Long> {

}
