package za.ac.cput.campus_events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.campus_events.domain.Faculty;
import za.ac.cput.campus_events.domain.Organiser;
import java.util.List;
import java.util.Optional;

@Repository

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Optional<Faculty> findByName(String name);
    List<Faculty> findByStatus(String status);
    List<Faculty> findByActive(boolean active);
    List<Faculty> findAll();
}
