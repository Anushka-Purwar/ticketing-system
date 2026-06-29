package booking_service.exceptions;

public class SeatAlreadyBookedException extends RuntimeException{
    public SeatAlreadyBookedException(String message){
        super(message);
    }
}
