package booking_service.dto;

import jakarta.validation.constraints.NotBlank;

public class TheaterRequest {

    @NotBlank(message = "Theater name is required")
    private String theaterName;
    @NotBlank(message = "City name is required")
    private String city;

    public String getName() {
        return theaterName;
    }

    public void setName(String theaterName) {
        this.theaterName = theaterName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}