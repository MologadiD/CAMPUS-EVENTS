package za.ac.cput.campus_events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.campus_events.domain.Venue;

@Repository
public interface git add src/main/java/za/ac/cput/campus_events/repository/VenueRepository.java
git commit -m "[Venue] Repository: add plain CRUD interface (#46)"
VenueRepository extends JpaRepository<Venue, Long> {
    // Plain CRUD is automatically provided by JpaRepository
    // No extra methods needed unless you want custom queries later
}
