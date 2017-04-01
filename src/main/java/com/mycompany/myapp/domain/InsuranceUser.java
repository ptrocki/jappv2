package com.mycompany.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A InsuranceUser.
 */
@Entity
@Table(name = "insurance_user")
public class InsuranceUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    private Insurance insurance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public InsuranceUser firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public InsuranceUser lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public InsuranceUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public InsuranceUser phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public InsuranceUser insurance(Insurance insurance) {
        this.insurance = insurance;
        return this;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InsuranceUser insuranceUser = (InsuranceUser) o;
        if (insuranceUser.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, insuranceUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InsuranceUser{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", email='" + email + "'" +
            ", phoneNumber='" + phoneNumber + "'" +
            '}';
    }
}
