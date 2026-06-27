package booking_service.controller;


import booking_service.dto.TheaterRequest;
import booking_service.entity.Theater;
import booking_service.service.TheaterService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theater")
public class TheaterController {
    private final TheaterService theaterService;

    public TheaterController( TheaterService theaterService){
        this.theaterService = theaterService;
    }

    @PostMapping
    public Theater create( @Valid  @RequestBody TheaterRequest theaterRequest){
        return theaterService.create(theaterRequest);
    }

    @GetMapping
    public List<Theater> getAllTheaters(){
        return theaterService.getAll();
    }
}
