package booking_service.controller;

import booking_service.dto.BookingRequest;
import booking_service.dto.SeatLockRequest;
import booking_service.entity.Booking;
import booking_service.exceptions.SeatAlreadyBookedException;
import booking_service.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking createBooking(@Valid @RequestBody BookingRequest request) {
        return bookingService.createBooking(request);
    }

    @PostMapping
    public ResponseEntity<String> lockSeat(@RequestBody @Valid SeatLockRequest request){
        boolean locked = bookingService.lockSeat(request);

        if (!locked) {
            throw new SeatAlreadyBookedException("Seat is currently locked");
        }

        return ResponseEntity.ok("Seat locked successfully");
    }
}
