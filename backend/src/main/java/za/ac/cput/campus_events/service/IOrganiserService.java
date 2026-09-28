package za.ac.cput.campus_events.service;

import za.ac.cput.campus_events.domain.Event;
import za.ac.cput.campus_events.domain.Organiser;

import java.util.List;
import java.util.Optional;

public interface IOrganiserService extends Iservice<Organiser, Long> {
    Organiser save(Organiser organiser);
    Optional<Organiser> findById(Long id);
    List<Organiser> findAll();
    void deleteById(Long id);

    Organiser registerOrganiser(Organiser organiser, Long facultyId);

    Event createEvent(Long organiserId, String title, String description,
                      java.time.LocalDateTime eventDate, Integer capacity, Long venueId);
    Event updateEvent(Long organiserId, Long eventId, String title, String description,
                      java.time.LocalDateTime eventDate, Integer capacity, Long venueId);
    void closeEvent(Long organiserId, Long eventId);

    void updateOrganiserStatus(Long organiserId, boolean active, Long requestingAdminId);
}
