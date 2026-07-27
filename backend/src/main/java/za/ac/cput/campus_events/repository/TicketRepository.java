package za.ac.cput.campus_events.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<TicketRepository, Long> {
    boolean existsByStudentIdAndEventIdAndStatusNot(Long studentId, Long eventId, String status);
}
