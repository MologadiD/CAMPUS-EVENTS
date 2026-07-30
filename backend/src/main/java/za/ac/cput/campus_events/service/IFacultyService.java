package za.ac.cput.campus_events.service;

import za.ac.cput.campus_events.domain.Faculty;

import java.util.List;
import java.util.Optional;

public interface IFacultyService extends Iservice<Faculty, Long> {
    Faculty save(Faculty faculty);
    Optional<Faculty> findById(Long id);
    List<Faculty> findAll();
    void deleteById(Long id);
    void deactivate(Long facultyId);
}
