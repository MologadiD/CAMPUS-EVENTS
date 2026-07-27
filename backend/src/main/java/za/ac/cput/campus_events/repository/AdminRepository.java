package za.ac.cput.campus_events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.campus_events.domain.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findAdminByEmailAndPassword(String email, String password);
}
