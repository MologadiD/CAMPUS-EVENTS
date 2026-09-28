package za.ac.cput.campus_events.service;

import org.springframework.stereotype.Service;
import za.ac.cput.campus_events.domain.Event;
import za.ac.cput.campus_events.domain.Faculty;
import za.ac.cput.campus_events.domain.Organiser;
import za.ac.cput.campus_events.domain.Venue;
import za.ac.cput.campus_events.repository.EventRepository;
import za.ac.cput.campus_events.repository.FacultyRepository;
import za.ac.cput.campus_events.repository.OrganiserRepository;
import za.ac.cput.campus_events.repository.VenueRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrganiserService implements IOrganiserService {

    private final OrganiserRepository organiserRepository;
    private final FacultyRepository facultyRepository;
    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public OrganiserService(OrganiserRepository organiserRepository,
                            FacultyRepository facultyRepository,
                            EventRepository eventRepository,
                            VenueRepository venueRepository) {
        this.organiserRepository = organiserRepository;
        this.facultyRepository = facultyRepository;
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    @Override
    public Organiser save(Organiser organiser) { return organiserRepository.save(organiser); }

    @Override
    public Optional<Organiser> findById(Long id) { return organiserRepository.findById(id); }

    @Override
    public List<Organiser> findAll() { return organiserRepository.findAll(); }

    @Override
    public void deleteById(Long id) { organiserRepository.deleteById(id); }

    @Override
    public Organiser registerOrganiser(Organiser organiser, Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new RuntimeException("Faculty not found: " + facultyId));
        if (!faculty.isActive()) {
            throw new RuntimeException("Cannot register organiser — faculty is not active");
        }
        return organiserRepository.save(organiser);
    }

    private Organiser requireActiveOrganiser(Long organiserId) {
        Organiser organiser = organiserRepository.findById(organiserId)
                .orElseThrow(() -> new RuntimeException("Organiser not found: " + organiserId));
        if (!organiser.isActive()) {
            throw new RuntimeException("Cannot manage event — organiser is suspended");
        }
        if (organiser.getFaculty() == null) {
            throw new RuntimeException("Faculty not found for organiser: " + organiserId);
        }
        if (!organiser.getFaculty().isActive()) {
            throw new RuntimeException("Cannot manage event — faculty is not active");
        }
        return organiser;
    }

    private Venue requireVenue(Long venueId) {
        return venueRepository.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found: " + venueId));
    }

    @Override
    public Event createEvent(Long organiserId, String title, String description,
                             LocalDateTime eventDate, Integer capacity, Long venueId) {
        Organiser organiser = requireActiveOrganiser(organiserId);
        Venue venue = requireVenue(venueId);

        Event event = new Event.Builder()
                .setTitle(title)
                .setDescription(description)
                .setEventDate(eventDate)
                .setCapacity(capacity)
                .setOpen(true)
                .setCreatedAt(LocalDateTime.now())
                .setVenue(venue)
                .setOrganiser(organiser)
                .setFaculty(organiser.getFaculty())
                .build();

        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Long organiserId, Long eventId, String title, String description,
                             LocalDateTime eventDate, Integer capacity, Long venueId) {
        Organiser organiser = requireActiveOrganiser(organiserId);
        Venue venue = requireVenue(venueId);
        Event existing = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found: " + eventId));

        if (existing.getOrganiser() == null || !organiserId.equals(existing.getOrganiser().getId())) {
            throw new RuntimeException("You can only update your own events");
        }

        Event updated = new Event(existing, title, description, eventDate, capacity, venue);
        return eventRepository.save(updated);
    }

    @Override
    public void closeEvent(Long organiserId, Long eventId) {
        Organiser organiser = requireActiveOrganiser(organiserId);
        Event existing = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found: " + eventId));

        if (existing.getOrganiser() == null || !organiserId.equals(existing.getOrganiser().getId())) {
            throw new RuntimeException("You can only close your own events");
        }

        eventRepository.save(new Event(existing, false));
    }

    @Override
    public void updateOrganiserStatus(Long organiserId, boolean active, Long requestingAdminId) {
        if (requestingAdminId == null) throw new IllegalStateException("Admin only");
        Organiser existing = organiserRepository.findById(organiserId)
                .orElseThrow(() -> new RuntimeException("Organiser not found: " + organiserId));
        organiserRepository.save(new Organiser(existing, active));
    }

    @Override public <T> T create(T t) { return null; }
    @Override public <T> T read(Long id) { return null; }
    @Override public <T> T update(T t) { return null; }
    @Override public <T> void delete(T t) { }
}
