package za.ac.cput.campus_events.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.campus_events.domain.Event;
import za.ac.cput.campus_events.repository.EventRepository;

import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // Register
    public Event registerStudent(Long eventId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            if (event.isOpen() && event.getCapacity() > 0) {
                event = new Event.Builder()
                        .setTitle(event.getTitle())
                        .setDescription(event.getDescription())
                        .setEventDate(event.getEventDate())
                        .setCapacity(event.getCapacity() - 1) // reduce capacity
                        .setOpen(event.getCapacity() - 1 > 0) // close if full
                        .setCreatedAt(event.getCreatedAt())
                        .setVenue(event.getVenue())
                        .build();
                return eventRepository.save(event);
            }
        }
        throw new IllegalStateException("Event not available for registration");
    }

    // Cancel
    public Event cancelEvent(Long eventId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event = new Event.Builder()
                    .setTitle(event.getTitle())
                    .setDescription(event.getDescription())
                    .setEventDate(event.getEventDate())
                    .setCapacity(event.getCapacity())
                    .setOpen(false) // mark as closed
                    .setCreatedAt(event.getCreatedAt())
                    .setVenue(event.getVenue())
                    .build();
            return eventRepository.save(event);
        }
        throw new IllegalStateException("Event not found");
    }
}
