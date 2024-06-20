package edu.ap.spring.infraction.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Infraction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @Column(name="infraction_year")
    public String year;
    @Column(name="infraction_month")
    public String month;
    @Column(name="infraction_date")
    public String date;
    @Column
    public String street;
    @Column
    public String driving_direction;
    @Column
    public String speed_limit;
    @Column
    public String passersby;
    @Column
    public String infractions_speed;
    @Column
    public String infractions_red_light;
}
