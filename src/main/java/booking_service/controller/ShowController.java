package booking_service.controller;

import booking_service.dto.ShowRequest;
import booking_service.entity.Show;
import booking_service.service.ShowService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows")
public class ShowController {
    private ShowService showService;

    public ShowController(ShowService showService){
        this.showService = showService;
    }

    @PostMapping
    public Show createShow(@Valid @RequestBody ShowRequest request){
        return showService.createShow(request);
    }

    @GetMapping
    public List<Show> getAllShows(){
        return showService.getAllShows();
    }
}
