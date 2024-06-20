package ap.jws.labo2.infractions.jpa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Infraction {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "\"year\"")
    private Long year;

    @Column(name = "\"month\"")
    private Long month;

    @Column(name = "\"date\"")
    private String date;

    @Column
    private String street;

    @Column
    private String driving_direction;

    @Column
    private int speed_limit;

    @Column
    private int passers_by;

    @Column
    private int infractions_speed;

    @Column
    private int infractions_red_light;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getYear() {
			return year;
		}

		public void setYear(Long year) {
			this.year = year;
		}

		public Long getMonth() {
			return month;
		}

		public void setMonth(Long month) {
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

		public int getSpeed_limit() {
			return speed_limit;
		}

		public void setSpeed_limit(int speed_limit) {
			this.speed_limit = speed_limit;
		}

		public int getPassers_by() {
			return passers_by;
		}

		public void setPassers_by(int passers_by) {
			this.passers_by = passers_by;
		}

		public int getInfractions_speed() {
			return infractions_speed;
		}

		public void setInfractions_speed(int infractions_speed) {
			this.infractions_speed = infractions_speed;
		}

		public int getInfractions_red_light() {
			return infractions_red_light;
		}

		public void setInfractions_red_light(int infractions_red_light) {
			this.infractions_red_light = infractions_red_light;
		}

		
}
