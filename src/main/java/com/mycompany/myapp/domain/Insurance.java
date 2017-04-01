package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Insurance.
 */
@Entity
@Table(name = "insurance")
public class Insurance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "insurance")
    @JsonIgnore
    private Set<InsuranceUser> insuranceUsers = new HashSet<>();

    @ManyToOne
    private InsuranceOption insuranceOption;

    @ManyToOne
    private Address address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Insurance name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<InsuranceUser> getInsuranceUsers() {
        return insuranceUsers;
    }

    public Insurance insuranceUsers(Set<InsuranceUser> insuranceUsers) {
        this.insuranceUsers = insuranceUsers;
        return this;
    }

    public Insurance addInsuranceUser(InsuranceUser insuranceUser) {
        this.insuranceUsers.add(insuranceUser);
        insuranceUser.setInsurance(this);
        return this;
    }

    public Insurance removeInsuranceUser(InsuranceUser insuranceUser) {
        this.insuranceUsers.remove(insuranceUser);
        insuranceUser.setInsurance(null);
        return this;
    }

    public void setInsuranceUsers(Set<InsuranceUser> insuranceUsers) {
        this.insuranceUsers = insuranceUsers;
    }

    public InsuranceOption getInsuranceOption() {
        return insuranceOption;
    }

    public Insurance insuranceOption(InsuranceOption insuranceOption) {
        this.insuranceOption = insuranceOption;
        return this;
    }

    public void setInsuranceOption(InsuranceOption insuranceOption) {
        this.insuranceOption = insuranceOption;
    }

    public Address getAddress() {
        return address;
    }

    public Insurance address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Insurance insurance = (Insurance) o;
        if (insurance.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, insurance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Insurance{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
