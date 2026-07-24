package za.ac.cput.campus_events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.campus_events.domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
