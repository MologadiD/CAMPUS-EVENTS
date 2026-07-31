package za.ac.cput.campus_events.service;
/*
Mologadi Dikgale
Student No: 231016263
 */

import za.ac.cput.campus_events.domain.Faculty;
import java.util.List;
import java.util.Optional;

public interface IFacultyService {
    Faculty save(Faculty faculty);
    Optional<Faculty> findById(Long id);
    List<Faculty> findAll();
    void deleteById(Long id);

    List<Faculty> findByStatus(String status);
}
