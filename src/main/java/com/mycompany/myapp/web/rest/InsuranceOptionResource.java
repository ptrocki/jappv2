package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.InsuranceOption;

import com.mycompany.myapp.repository.InsuranceOptionRepository;
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
 * REST controller for managing InsuranceOption.
 */
@RestController
@RequestMapping("/api")
public class InsuranceOptionResource {

    private final Logger log = LoggerFactory.getLogger(InsuranceOptionResource.class);

    private static final String ENTITY_NAME = "insuranceOption";
        
    private final InsuranceOptionRepository insuranceOptionRepository;

    public InsuranceOptionResource(InsuranceOptionRepository insuranceOptionRepository) {
        this.insuranceOptionRepository = insuranceOptionRepository;
    }

    /**
     * POST  /insurance-options : Create a new insuranceOption.
     *
     * @param insuranceOption the insuranceOption to create
     * @return the ResponseEntity with status 201 (Created) and with body the new insuranceOption, or with status 400 (Bad Request) if the insuranceOption has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/insurance-options")
    @Timed
    public ResponseEntity<InsuranceOption> createInsuranceOption(@RequestBody InsuranceOption insuranceOption) throws URISyntaxException {
        log.debug("REST request to save InsuranceOption : {}", insuranceOption);
        if (insuranceOption.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new insuranceOption cannot already have an ID")).body(null);
        }
        InsuranceOption result = insuranceOptionRepository.save(insuranceOption);
        return ResponseEntity.created(new URI("/api/insurance-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /insurance-options : Updates an existing insuranceOption.
     *
     * @param insuranceOption the insuranceOption to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated insuranceOption,
     * or with status 400 (Bad Request) if the insuranceOption is not valid,
     * or with status 500 (Internal Server Error) if the insuranceOption couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/insurance-options")
    @Timed
    public ResponseEntity<InsuranceOption> updateInsuranceOption(@RequestBody InsuranceOption insuranceOption) throws URISyntaxException {
        log.debug("REST request to update InsuranceOption : {}", insuranceOption);
        if (insuranceOption.getId() == null) {
            return createInsuranceOption(insuranceOption);
        }
        InsuranceOption result = insuranceOptionRepository.save(insuranceOption);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, insuranceOption.getId().toString()))
            .body(result);
    }

    /**
     * GET  /insurance-options : get all the insuranceOptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of insuranceOptions in body
     */
    @GetMapping("/insurance-options")
    @Timed
    public List<InsuranceOption> getAllInsuranceOptions() {
        log.debug("REST request to get all InsuranceOptions");
        List<InsuranceOption> insuranceOptions = insuranceOptionRepository.findAll();
        return insuranceOptions;
    }

    /**
     * GET  /insurance-options/:id : get the "id" insuranceOption.
     *
     * @param id the id of the insuranceOption to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the insuranceOption, or with status 404 (Not Found)
     */
    @GetMapping("/insurance-options/{id}")
    @Timed
    public ResponseEntity<InsuranceOption> getInsuranceOption(@PathVariable Long id) {
        log.debug("REST request to get InsuranceOption : {}", id);
        InsuranceOption insuranceOption = insuranceOptionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(insuranceOption));
    }

    /**
     * DELETE  /insurance-options/:id : delete the "id" insuranceOption.
     *
     * @param id the id of the insuranceOption to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/insurance-options/{id}")
    @Timed
    public ResponseEntity<Void> deleteInsuranceOption(@PathVariable Long id) {
        log.debug("REST request to delete InsuranceOption : {}", id);
        insuranceOptionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
