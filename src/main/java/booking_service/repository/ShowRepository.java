package booking_service.repository;

import booking_service.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Long> {

    boolean existsByTheaterIdAndStartTime(Long theaterId, String startTime);
}
