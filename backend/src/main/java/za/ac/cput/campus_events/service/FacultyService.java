package za.ac.cput.campus_events.service;
/*
Mologadi Dikgale
Student No: 231016263
 */

import org.springframework.stereotype.Service;
import za.ac.cput.campus_events.domain.Event;
import za.ac.cput.campus_events.domain.Faculty;
import za.ac.cput.campus_events.domain.Organiser;
import za.ac.cput.campus_events.repository.EventRepository;
import za.ac.cput.campus_events.repository.FacultyRepository;
import za.ac.cput.campus_events.repository.OrganiserRepository;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyService  implements IFacultyService{

    private final FacultyRepository   facultyRepository;
    private final OrganiserRepository organiserRepository;
    private final EventRepository     eventRepository;

    public FacultyService(FacultyRepository facultyRepository,
                          OrganiserRepository organiserRepository,
                          EventRepository eventRepository) {
        this.facultyRepository   = facultyRepository;
        this.organiserRepository = organiserRepository;
        this.eventRepository     = eventRepository;
    }

    @Override
    public Faculty save(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Optional<Faculty> findById(Long id) {
        return facultyRepository.findById(id);
    }

    @Override
    public List<Faculty> findAll() {
        return facultyRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        facultyRepository.deleteById(id);
    }


    @Override
    public void deactivate(Long facultyId) {

        // 1. Find the faculty — throw if not found
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new RuntimeException(
                        "Faculty not found with id: " + facultyId));

        // 2. Find all organisers belonging to this faculty
        List<Organiser> organisers = organiserRepository
                .findByFacultyId(facultyId);

        // 3. For each organiser, close all their open events
        for (Organiser organiser : organisers) {
            List<Event> events = eventRepository
                    .findByOrganiserId(organiser.getId());

            for (Event event : events) {
                if (event.isOpen()) {
                    event.closeRegistration();
                    eventRepository.save(event);
                }
            }
        }

        Faculty updatedFaculty = new Faculty.Builder()
                .id(faculty.getId())
                .name(faculty.getName())
                .status("INACTIVE")
                .contactEmail(faculty.getContactEmail())
                .createdByAdminId(faculty.getCreatedByAdminId())
                .createdAt(faculty.getCreatedAt())
                .build();

        facultyRepository.save(updatedFaculty);
    }

}
