package com.examen.vraag2.datecheck.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class DateCheck {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private LocalDate checkedDate;

    @Column
    private Boolean inBetween;

    @Column
    private Long daysRemoved;

    public DateCheck() {
    }

    public DateCheck(LocalDate checkedDate, Boolean inBetween, Long daysRemoved) {
        this.checkedDate = checkedDate;
        this.inBetween = inBetween;
        this.daysRemoved = daysRemoved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCheckedDate() {
        return checkedDate;
    }

    public void setCheckedDate(LocalDate checkedDate) {
        this.checkedDate = checkedDate;
    }

    public Boolean getInBetween() {
        return inBetween;
    }

    public void setInBetween(Boolean inBetween) {
        this.inBetween = inBetween;
    }

    public Long getDaysRemoved() {
        return daysRemoved;
    }

    public void setDaysRemoved(Long daysRemoved) {
        this.daysRemoved = daysRemoved;
    }
}
