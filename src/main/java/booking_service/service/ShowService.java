package booking_service.service;

import booking_service.dto.ShowRequest;
import booking_service.entity.Show;
import booking_service.entity.Theater;
import booking_service.repository.ShowRepository;
import booking_service.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {
    private final ShowRepository showRepository;
    private final TheaterRepository theaterRepository;

    public ShowService(ShowRepository showRepository, TheaterRepository theaterRepository){
        this.showRepository = showRepository;
        this.theaterRepository = theaterRepository;
    }

    public Show createShow(ShowRequest request) {

        Theater theater = theaterRepository.findById(request.getTheaterId())
                .orElseThrow(() ->
                        new RuntimeException("Theater not found"));

        Show show = new Show();
        show.setMovieName(request.getMovieName());
        show.setStartTime(request.getStartTime());
        show.setTheater(theater);

        return showRepository.save(show);
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }
}
