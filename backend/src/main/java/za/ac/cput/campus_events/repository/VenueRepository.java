package za.ac.cput.campus_events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.campus_events.domain.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    // Plain CRUD methods are inherited from JpaRepository:
    // save(), findById(), findAll(), deleteById(), etc.
}
