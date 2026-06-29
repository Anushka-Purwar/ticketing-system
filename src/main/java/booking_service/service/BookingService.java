package booking_service.service;

import booking_service.dto.BookingRequest;
import booking_service.entity.Booking;
import booking_service.entity.Seat;
import booking_service.entity.Show;
import booking_service.enums.SeatStatus;
import booking_service.exceptions.InvalidBookingException;
import booking_service.exceptions.ResourceNotFoundException;
import booking_service.exceptions.SeatAlreadyBookedException;
import booking_service.repository.BookingRepository;
import booking_service.repository.SeatRepository;
import booking_service.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingService {
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final BookingRepository bookingRepository;

    public BookingService(ShowRepository showRepository, SeatRepository seatRepository, BookingRepository bookingRepository) {
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.bookingRepository = bookingRepository;
    }

    public Booking createBooking(BookingRequest request){
        Show show  = showRepository.findById(request.getShowId()).
                orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        Seat seat = seatRepository.findById(request.getSeatId()).
                orElseThrow(() -> new ResourceNotFoundException("No seat available for this show at this moment"));

        if(!seat.getShow().getId().equals(request.getShowId())){
            throw new InvalidBookingException("Seat does not belong to the selected show");
        }

        if(seat.getStatus() == SeatStatus.BOOKED){
            throw new SeatAlreadyBookedException("This seat is already booked");
        }

        Booking booking = new Booking();
        booking.setUserName(request.getUserName());
        booking.setBookingDate(LocalDateTime.now());
        booking.setShow(show);
        booking.setSeat(seat);

        seat.setStatus(SeatStatus.BOOKED);
        seatRepository.save(seat);
        return bookingRepository.save(booking);
    }
}
