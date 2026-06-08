package booking_service.service;

import booking_service.dto.ShowRequest;
import booking_service.entity.Show;
import booking_service.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {
    private ShowRepository showRepository;

    public ShowService(ShowRepository showRepository){
        this.showRepository = showRepository;
    }

    public Show createShow(ShowRequest request) {
        Show show = new Show();
        show.setMovieName(request.getMovieName());
        show.setStartTime(request.getStartTime());

        return showRepository.save(show);
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }
}
