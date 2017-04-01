package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Jappv2App;

import com.mycompany.myapp.domain.InsuranceOption;
import com.mycompany.myapp.repository.InsuranceOptionRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InsuranceOptionResource REST controller.
 *
 * @see InsuranceOptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Jappv2App.class)
public class InsuranceOptionResourceIntTest {

    private static final Boolean DEFAULT_FISRT = false;
    private static final Boolean UPDATED_FISRT = true;

    private static final Boolean DEFAULT_SECOND = false;
    private static final Boolean UPDATED_SECOND = true;

    private static final Boolean DEFAULT_THIRD = false;
    private static final Boolean UPDATED_THIRD = true;

    private static final Boolean DEFAULT_FOURTH = false;
    private static final Boolean UPDATED_FOURTH = true;

    @Autowired
    private InsuranceOptionRepository insuranceOptionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInsuranceOptionMockMvc;

    private InsuranceOption insuranceOption;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InsuranceOptionResource insuranceOptionResource = new InsuranceOptionResource(insuranceOptionRepository);
        this.restInsuranceOptionMockMvc = MockMvcBuilders.standaloneSetup(insuranceOptionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InsuranceOption createEntity(EntityManager em) {
        InsuranceOption insuranceOption = new InsuranceOption()
            .fisrt(DEFAULT_FISRT)
            .second(DEFAULT_SECOND)
            .third(DEFAULT_THIRD)
            .fourth(DEFAULT_FOURTH);
        return insuranceOption;
    }

    @Before
    public void initTest() {
        insuranceOption = createEntity(em);
    }

    @Test
    @Transactional
    public void createInsuranceOption() throws Exception {
        int databaseSizeBeforeCreate = insuranceOptionRepository.findAll().size();

        // Create the InsuranceOption
        restInsuranceOptionMockMvc.perform(post("/api/insurance-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceOption)))
            .andExpect(status().isCreated());

        // Validate the InsuranceOption in the database
        List<InsuranceOption> insuranceOptionList = insuranceOptionRepository.findAll();
        assertThat(insuranceOptionList).hasSize(databaseSizeBeforeCreate + 1);
        InsuranceOption testInsuranceOption = insuranceOptionList.get(insuranceOptionList.size() - 1);
        assertThat(testInsuranceOption.isFisrt()).isEqualTo(DEFAULT_FISRT);
        assertThat(testInsuranceOption.isSecond()).isEqualTo(DEFAULT_SECOND);
        assertThat(testInsuranceOption.isThird()).isEqualTo(DEFAULT_THIRD);
        assertThat(testInsuranceOption.isFourth()).isEqualTo(DEFAULT_FOURTH);
    }

    @Test
    @Transactional
    public void createInsuranceOptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = insuranceOptionRepository.findAll().size();

        // Create the InsuranceOption with an existing ID
        insuranceOption.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsuranceOptionMockMvc.perform(post("/api/insurance-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceOption)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<InsuranceOption> insuranceOptionList = insuranceOptionRepository.findAll();
        assertThat(insuranceOptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInsuranceOptions() throws Exception {
        // Initialize the database
        insuranceOptionRepository.saveAndFlush(insuranceOption);

        // Get all the insuranceOptionList
        restInsuranceOptionMockMvc.perform(get("/api/insurance-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insuranceOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].fisrt").value(hasItem(DEFAULT_FISRT.booleanValue())))
            .andExpect(jsonPath("$.[*].second").value(hasItem(DEFAULT_SECOND.booleanValue())))
            .andExpect(jsonPath("$.[*].third").value(hasItem(DEFAULT_THIRD.booleanValue())))
            .andExpect(jsonPath("$.[*].fourth").value(hasItem(DEFAULT_FOURTH.booleanValue())));
    }

    @Test
    @Transactional
    public void getInsuranceOption() throws Exception {
        // Initialize the database
        insuranceOptionRepository.saveAndFlush(insuranceOption);

        // Get the insuranceOption
        restInsuranceOptionMockMvc.perform(get("/api/insurance-options/{id}", insuranceOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(insuranceOption.getId().intValue()))
            .andExpect(jsonPath("$.fisrt").value(DEFAULT_FISRT.booleanValue()))
            .andExpect(jsonPath("$.second").value(DEFAULT_SECOND.booleanValue()))
            .andExpect(jsonPath("$.third").value(DEFAULT_THIRD.booleanValue()))
            .andExpect(jsonPath("$.fourth").value(DEFAULT_FOURTH.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInsuranceOption() throws Exception {
        // Get the insuranceOption
        restInsuranceOptionMockMvc.perform(get("/api/insurance-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInsuranceOption() throws Exception {
        // Initialize the database
        insuranceOptionRepository.saveAndFlush(insuranceOption);
        int databaseSizeBeforeUpdate = insuranceOptionRepository.findAll().size();

        // Update the insuranceOption
        InsuranceOption updatedInsuranceOption = insuranceOptionRepository.findOne(insuranceOption.getId());
        updatedInsuranceOption
            .fisrt(UPDATED_FISRT)
            .second(UPDATED_SECOND)
            .third(UPDATED_THIRD)
            .fourth(UPDATED_FOURTH);

        restInsuranceOptionMockMvc.perform(put("/api/insurance-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInsuranceOption)))
            .andExpect(status().isOk());

        // Validate the InsuranceOption in the database
        List<InsuranceOption> insuranceOptionList = insuranceOptionRepository.findAll();
        assertThat(insuranceOptionList).hasSize(databaseSizeBeforeUpdate);
        InsuranceOption testInsuranceOption = insuranceOptionList.get(insuranceOptionList.size() - 1);
        assertThat(testInsuranceOption.isFisrt()).isEqualTo(UPDATED_FISRT);
        assertThat(testInsuranceOption.isSecond()).isEqualTo(UPDATED_SECOND);
        assertThat(testInsuranceOption.isThird()).isEqualTo(UPDATED_THIRD);
        assertThat(testInsuranceOption.isFourth()).isEqualTo(UPDATED_FOURTH);
    }

    @Test
    @Transactional
    public void updateNonExistingInsuranceOption() throws Exception {
        int databaseSizeBeforeUpdate = insuranceOptionRepository.findAll().size();

        // Create the InsuranceOption

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInsuranceOptionMockMvc.perform(put("/api/insurance-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceOption)))
            .andExpect(status().isCreated());

        // Validate the InsuranceOption in the database
        List<InsuranceOption> insuranceOptionList = insuranceOptionRepository.findAll();
        assertThat(insuranceOptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInsuranceOption() throws Exception {
        // Initialize the database
        insuranceOptionRepository.saveAndFlush(insuranceOption);
        int databaseSizeBeforeDelete = insuranceOptionRepository.findAll().size();

        // Get the insuranceOption
        restInsuranceOptionMockMvc.perform(delete("/api/insurance-options/{id}", insuranceOption.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InsuranceOption> insuranceOptionList = insuranceOptionRepository.findAll();
        assertThat(insuranceOptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsuranceOption.class);
    }
}
