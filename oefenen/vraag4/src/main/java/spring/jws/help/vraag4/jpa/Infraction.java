package spring.jws.help.vraag4.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Infraction implements Comparable<Infraction> {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String year;

    @Column
    private String month;

    @Column
    private String date;

    @Column
    private String street;

    @Column
    private String driving_direction;

    @Column
    private String speed_limit;

    @Column
    private String passersby;

    @Column
    private String infractions_speed;

    @Column
    private String infractions_red_light;

    public Infraction() {
    }

    public Infraction(String year, String month, String date, String street, String driving_direction, String speed_limit, String passersby, String infractions_speed, String infractions_red_light) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.street = street;
        this.driving_direction = driving_direction;
        this.speed_limit = speed_limit;
        this.passersby = passersby;
        this.infractions_speed = infractions_speed;
        this.infractions_red_light = infractions_red_light;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDriving_direction() {
        return driving_direction;
    }

    public void setDriving_direction(String driving_direction) {
        this.driving_direction = driving_direction;
    }

    public String getSpeed_limit() {
        return speed_limit;
    }

    public void setSpeed_limit(String speed_limit) {
        this.speed_limit = speed_limit;
    }

    public String getPassersby() {
        return passersby;
    }

    public void setPassersby(String passersby) {
        this.passersby = passersby;
    }

    public String getInfractions_speed() {
        return infractions_speed;
    }

    public void setInfractions_speed(String infractions_speed) {
        this.infractions_speed = infractions_speed;
    }

    public String getInfractions_red_light() {
        return infractions_red_light;
    }

    public void setInfractions_red_light(String infractions_red_light) {
        this.infractions_red_light = infractions_red_light;
    }

    public int compareTo(Infraction ov) {
        try {
            if (Integer.parseInt(this.infractions_speed) == Integer.parseInt(ov.infractions_speed))
                return 0;
            else if (Integer.parseInt(this.infractions_speed) > Integer.parseInt(ov.infractions_speed))
                return 1;
            else
                return -1;
        } catch (Exception ex) {
            return -1;
        }
    }
}