package za.ac.cput.campus_events.repository;
/*
Mologadi Dikgale
Student no: 231016263
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.campus_events.domain.Faculty;
import za.ac.cput.campus_events.domain.Organiser;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Organiser, Long> {
    List<Faculty> findByStatus(String status);
}
