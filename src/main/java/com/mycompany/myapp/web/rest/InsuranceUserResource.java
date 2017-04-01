package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.InsuranceUser;

import com.mycompany.myapp.repository.InsuranceUserRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing InsuranceUser.
 */
@RestController
@RequestMapping("/api")
public class InsuranceUserResource {

    private final Logger log = LoggerFactory.getLogger(InsuranceUserResource.class);

    private static final String ENTITY_NAME = "insuranceUser";
        
    private final InsuranceUserRepository insuranceUserRepository;

    public InsuranceUserResource(InsuranceUserRepository insuranceUserRepository) {
        this.insuranceUserRepository = insuranceUserRepository;
    }

    /**
     * POST  /insurance-users : Create a new insuranceUser.
     *
     * @param insuranceUser the insuranceUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new insuranceUser, or with status 400 (Bad Request) if the insuranceUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/insurance-users")
    @Timed
    public ResponseEntity<InsuranceUser> createInsuranceUser(@RequestBody InsuranceUser insuranceUser) throws URISyntaxException {
        log.debug("REST request to save InsuranceUser : {}", insuranceUser);
        if (insuranceUser.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new insuranceUser cannot already have an ID")).body(null);
        }
        InsuranceUser result = insuranceUserRepository.save(insuranceUser);
        return ResponseEntity.created(new URI("/api/insurance-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /insurance-users : Updates an existing insuranceUser.
     *
     * @param insuranceUser the insuranceUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated insuranceUser,
     * or with status 400 (Bad Request) if the insuranceUser is not valid,
     * or with status 500 (Internal Server Error) if the insuranceUser couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/insurance-users")
    @Timed
    public ResponseEntity<InsuranceUser> updateInsuranceUser(@RequestBody InsuranceUser insuranceUser) throws URISyntaxException {
        log.debug("REST request to update InsuranceUser : {}", insuranceUser);
        if (insuranceUser.getId() == null) {
            return createInsuranceUser(insuranceUser);
        }
        InsuranceUser result = insuranceUserRepository.save(insuranceUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, insuranceUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /insurance-users : get all the insuranceUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of insuranceUsers in body
     */
    @GetMapping("/insurance-users")
    @Timed
    public List<InsuranceUser> getAllInsuranceUsers() {
        log.debug("REST request to get all InsuranceUsers");
        List<InsuranceUser> insuranceUsers = insuranceUserRepository.findAll();
        return insuranceUsers;
    }

    /**
     * GET  /insurance-users/:id : get the "id" insuranceUser.
     *
     * @param id the id of the insuranceUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the insuranceUser, or with status 404 (Not Found)
     */
    @GetMapping("/insurance-users/{id}")
    @Timed
    public ResponseEntity<InsuranceUser> getInsuranceUser(@PathVariable Long id) {
        log.debug("REST request to get InsuranceUser : {}", id);
        InsuranceUser insuranceUser = insuranceUserRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(insuranceUser));
    }

    /**
     * DELETE  /insurance-users/:id : delete the "id" insuranceUser.
     *
     * @param id the id of the insuranceUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/insurance-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteInsuranceUser(@PathVariable Long id) {
        log.debug("REST request to delete InsuranceUser : {}", id);
        insuranceUserRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
