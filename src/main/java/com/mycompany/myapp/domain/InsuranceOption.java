package com.mycompany.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A InsuranceOption.
 */
@Entity
@Table(name = "insurance_option")
public class InsuranceOption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fisrt")
    private Boolean fisrt;

    @Column(name = "second")
    private Boolean second;

    @Column(name = "third")
    private Boolean third;

    @Column(name = "fourth")
    private Boolean fourth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isFisrt() {
        return fisrt;
    }

    public InsuranceOption fisrt(Boolean fisrt) {
        this.fisrt = fisrt;
        return this;
    }

    public void setFisrt(Boolean fisrt) {
        this.fisrt = fisrt;
    }

    public Boolean isSecond() {
        return second;
    }

    public InsuranceOption second(Boolean second) {
        this.second = second;
        return this;
    }

    public void setSecond(Boolean second) {
        this.second = second;
    }

    public Boolean isThird() {
        return third;
    }

    public InsuranceOption third(Boolean third) {
        this.third = third;
        return this;
    }

    public void setThird(Boolean third) {
        this.third = third;
    }

    public Boolean isFourth() {
        return fourth;
    }

    public InsuranceOption fourth(Boolean fourth) {
        this.fourth = fourth;
        return this;
    }

    public void setFourth(Boolean fourth) {
        this.fourth = fourth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InsuranceOption insuranceOption = (InsuranceOption) o;
        if (insuranceOption.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, insuranceOption.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InsuranceOption{" +
            "id=" + id +
            ", fisrt='" + fisrt + "'" +
            ", second='" + second + "'" +
            ", third='" + third + "'" +
            ", fourth='" + fourth + "'" +
            '}';
    }
}
