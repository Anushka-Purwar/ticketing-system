package booking_service.service;

import booking_service.dto.BookingRequest;
import booking_service.dto.ConfirmBookingRequest;
import booking_service.dto.SeatLockRequest;
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
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingService {
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final BookingRepository bookingRepository;
    private final RedisSeatLockService redisSeatLockService;

    public BookingService(ShowRepository showRepository, SeatRepository seatRepository, BookingRepository bookingRepository, RedisSeatLockService redisSeatLockService) {
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.bookingRepository = bookingRepository;
        this.redisSeatLockService = redisSeatLockService;
    }


    private Seat validateSeatAndShow(Long seatId, Long showId) {

        showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No seat available for this show at this moment"
                        ));

        if (!seat.getShow().getId().equals(showId)) {
            throw new InvalidBookingException(
                    "Seat does not belong to the selected show"
            );
        }

        if (seat.getStatus() == SeatStatus.BOOKED) {
            throw new SeatAlreadyBookedException(
                    "This seat is already booked"
            );
        }

        return seat;
    }
    @Transactional
    public Booking createBooking(BookingRequest request){
        Seat seat = validateSeatAndShow(request.getSeatId(),request.getShowId());
        Show show  = seat.getShow();

        Booking booking = new Booking();
        booking.setUserName(request.getUserName());
        booking.setBookingDate(LocalDateTime.now());
        booking.setShow(show);
        booking.setSeat(seat);

        seat.setStatus(SeatStatus.BOOKED);
        seatRepository.save(seat);
        return bookingRepository.save(booking);
    }
    @Transactional
    public Booking confirmBooking(ConfirmBookingRequest request) {

        String lockOwner = redisSeatLockService.getLockOwner(
                request.getShowId(),
                request.getSeatId()
        );

        if (lockOwner == null) {
            throw new InvalidBookingException("Seat lock has expired or does not exist");
        }

        if (!lockOwner.equals(request.getUserName())) {
            throw new InvalidBookingException("Seat is locked by another user");
        }

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setShowId(request.getShowId());
        bookingRequest.setSeatId(request.getSeatId());
        bookingRequest.setUserName(request.getUserName());

        Booking booking = createBooking(bookingRequest);

        redisSeatLockService.unlockSeat(
                request.getShowId(),
                request.getSeatId()
        );

        return booking;
    }

    public boolean lockSeat(SeatLockRequest request){
        validateSeatAndShow(request.getSeatId(),request.getShowId());
        return redisSeatLockService.lockSeat(
                request.getShowId(),
                request.getSeatId(),
                request.getUserName()
        );
    }
}
