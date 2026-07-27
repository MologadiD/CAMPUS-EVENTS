package za.ac.cput.campus_events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.campus_events.domain.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    // Plain CRUD is automatically provided by JpaRepository.
    // No extra methods needed unless if custom queries are added  later
}
