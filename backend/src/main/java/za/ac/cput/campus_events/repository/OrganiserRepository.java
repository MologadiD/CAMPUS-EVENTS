package za.ac.cput.campus_events.repository;
/*
Mologadi Dikgale
Student no: 231016263
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.campus_events.domain.Organiser;

@Repository
public interface OrganiserRepository extends JpaRepository<Organiser, Long> {
}
