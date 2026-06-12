package booking_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "shows") // define table name
public class Show {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY) // auto increment ID
    private Long id;
    private String movieName;
    private String startTime;

    @ManyToOne
    @JoinColumn(name = "theater_id")
    private  Theater theater;

    public Show(){

    }

    public Long getId(){
        return id;
    }

    public String getMovieName(){
        return movieName;
    }

    public void setMovieName(String movieName){
        this.movieName = movieName;
    }

    public String getStartTime(){
        return startTime;
    }

    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }
}
