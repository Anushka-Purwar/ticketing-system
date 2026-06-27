package booking_service.service;

import booking_service.dto.TheaterRequest;
import booking_service.entity.Theater;
import booking_service.exceptions.DuplicateResourceException;
import booking_service.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TheaterService {
    private TheaterRepository theaterRepository;

    public TheaterService(TheaterRepository theaterRepository){
        this.theaterRepository = theaterRepository;
    }

    public Theater create(TheaterRequest request) {

        Theater theater = new Theater();

        theater.setTheaterName(request.getName());
        theater.setCity(request.getCity());

        if (theaterRepository.existsByTheaterName(request.getName())) {
            throw new DuplicateResourceException("Theater already exists");
        }

        return theaterRepository.save(theater);
    }

    public List<Theater> getAll() {
        return theaterRepository.findAll();
    }
}
