package booking_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SeatLockRequest {
    @NotNull
    Long showId;
    @NotNull
    Long seatId;
    @NotBlank
    String userName;

    public Long getSeatId() {
        return seatId;
    }

    public Long getShowId() {
        return showId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
