package za.ac.cput.campus_events.service;

public interface IFacultyService extends Iservice<Faculty, Long> {
    Faculty save(Faculty faculty);
    Optional<Faculty> findById(Long id);
    List<Faculty> findAll();
    void deleteById(Long id);
    void deactivate(Long facultyId);
}
