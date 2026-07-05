package booking_service.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "Theater")
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String theaterName;
    private String city;

    public Theater(){

    }

    public Long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }
}
