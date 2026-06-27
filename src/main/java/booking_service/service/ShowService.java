package booking_service.service;

import booking_service.dto.ShowRequest;
import booking_service.entity.Seat;
import booking_service.entity.Show;
import booking_service.entity.Theater;
import booking_service.enums.SeatStatus;
import booking_service.exceptions.ResourceNotFoundException;
import booking_service.repository.SeatRepository;
import booking_service.repository.ShowRepository;
import booking_service.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowService {
    private final ShowRepository showRepository;
    private final TheaterRepository theaterRepository;
    private final SeatRepository seatRepository;

    public ShowService(ShowRepository showRepository, TheaterRepository theaterRepository, SeatRepository seatRepository){
        this.showRepository = showRepository;
        this.theaterRepository = theaterRepository;
        this.seatRepository = seatRepository;
    }

    public Show createShow(ShowRequest request) {

        Theater theater = theaterRepository.findById(request.getTheaterId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Theater not found for this particular ID"));

        List<Seat> seats = new ArrayList<>();

        Show show = new Show();
        show.setMovieName(request.getMovieName());
        show.setStartTime(request.getStartTime());
        show.setTheater(theater);
        Show savedShow = showRepository.save(show);
        for(int i=1;i<=5;i++){
            Seat seat = new Seat();

            seat.setSeatNumber("A" + i);
            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setShow(savedShow);
            seats.add(seat);
        }
        seatRepository.saveAll(seats);

        return savedShow;
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }
}
