package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Jappv2App;

import com.mycompany.myapp.domain.InsuranceUser;
import com.mycompany.myapp.repository.InsuranceUserRepository;
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
 * Test class for the InsuranceUserResource REST controller.
 *
 * @see InsuranceUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Jappv2App.class)
public class InsuranceUserResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private InsuranceUserRepository insuranceUserRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInsuranceUserMockMvc;

    private InsuranceUser insuranceUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InsuranceUserResource insuranceUserResource = new InsuranceUserResource(insuranceUserRepository);
        this.restInsuranceUserMockMvc = MockMvcBuilders.standaloneSetup(insuranceUserResource)
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
    public static InsuranceUser createEntity(EntityManager em) {
        InsuranceUser insuranceUser = new InsuranceUser()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return insuranceUser;
    }

    @Before
    public void initTest() {
        insuranceUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createInsuranceUser() throws Exception {
        int databaseSizeBeforeCreate = insuranceUserRepository.findAll().size();

        // Create the InsuranceUser
        restInsuranceUserMockMvc.perform(post("/api/insurance-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceUser)))
            .andExpect(status().isCreated());

        // Validate the InsuranceUser in the database
        List<InsuranceUser> insuranceUserList = insuranceUserRepository.findAll();
        assertThat(insuranceUserList).hasSize(databaseSizeBeforeCreate + 1);
        InsuranceUser testInsuranceUser = insuranceUserList.get(insuranceUserList.size() - 1);
        assertThat(testInsuranceUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testInsuranceUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testInsuranceUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testInsuranceUser.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void createInsuranceUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = insuranceUserRepository.findAll().size();

        // Create the InsuranceUser with an existing ID
        insuranceUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsuranceUserMockMvc.perform(post("/api/insurance-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceUser)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<InsuranceUser> insuranceUserList = insuranceUserRepository.findAll();
        assertThat(insuranceUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInsuranceUsers() throws Exception {
        // Initialize the database
        insuranceUserRepository.saveAndFlush(insuranceUser);

        // Get all the insuranceUserList
        restInsuranceUserMockMvc.perform(get("/api/insurance-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insuranceUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getInsuranceUser() throws Exception {
        // Initialize the database
        insuranceUserRepository.saveAndFlush(insuranceUser);

        // Get the insuranceUser
        restInsuranceUserMockMvc.perform(get("/api/insurance-users/{id}", insuranceUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(insuranceUser.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInsuranceUser() throws Exception {
        // Get the insuranceUser
        restInsuranceUserMockMvc.perform(get("/api/insurance-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInsuranceUser() throws Exception {
        // Initialize the database
        insuranceUserRepository.saveAndFlush(insuranceUser);
        int databaseSizeBeforeUpdate = insuranceUserRepository.findAll().size();

        // Update the insuranceUser
        InsuranceUser updatedInsuranceUser = insuranceUserRepository.findOne(insuranceUser.getId());
        updatedInsuranceUser
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER);

        restInsuranceUserMockMvc.perform(put("/api/insurance-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInsuranceUser)))
            .andExpect(status().isOk());

        // Validate the InsuranceUser in the database
        List<InsuranceUser> insuranceUserList = insuranceUserRepository.findAll();
        assertThat(insuranceUserList).hasSize(databaseSizeBeforeUpdate);
        InsuranceUser testInsuranceUser = insuranceUserList.get(insuranceUserList.size() - 1);
        assertThat(testInsuranceUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testInsuranceUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testInsuranceUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInsuranceUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingInsuranceUser() throws Exception {
        int databaseSizeBeforeUpdate = insuranceUserRepository.findAll().size();

        // Create the InsuranceUser

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInsuranceUserMockMvc.perform(put("/api/insurance-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceUser)))
            .andExpect(status().isCreated());

        // Validate the InsuranceUser in the database
        List<InsuranceUser> insuranceUserList = insuranceUserRepository.findAll();
        assertThat(insuranceUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInsuranceUser() throws Exception {
        // Initialize the database
        insuranceUserRepository.saveAndFlush(insuranceUser);
        int databaseSizeBeforeDelete = insuranceUserRepository.findAll().size();

        // Get the insuranceUser
        restInsuranceUserMockMvc.perform(delete("/api/insurance-users/{id}", insuranceUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InsuranceUser> insuranceUserList = insuranceUserRepository.findAll();
        assertThat(insuranceUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsuranceUser.class);
    }
}
