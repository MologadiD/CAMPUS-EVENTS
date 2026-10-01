package za.ac.cput.campus_events.domain;

import jakarta.persistence.*;
import org.springframework.web.ErrorResponse;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime eventDate;
    private Integer capacity;
    private Boolean open;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @ManyToOne
    @JoinColumn(name = "organiser_id", nullable = false)
    private Organiser organiser;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ticket> tickets = new HashSet<>();

    private Event(Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.eventDate = builder.eventDate;
        this.capacity = builder.capacity;
        this.open = builder.open;
        this.createdAt = builder.createdAt;
        this.venue = builder.venue;
        this.organiser = builder.organiser;
        this.faculty = builder.faculty;
        this.tickets = builder.tickets;
    }

    public Event() {}

    public Event(Event existing, String title, String description,
                 LocalDateTime eventDate, Integer capacity, Venue venue) {
        this.id = existing.id;
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.capacity = capacity;
        this.open = existing.open;
        this.createdAt = existing.createdAt;
        this.venue = venue;
        this.organiser = existing.organiser;
        this.faculty = existing.faculty;
        this.tickets = existing.tickets;
    }

    public Event(Event existing, boolean open) {
        this.id = existing.id;
        this.title = existing.title;
        this.description = existing.description;
        this.eventDate = existing.eventDate;
        this.capacity = existing.capacity;
        this.open = open;
        this.createdAt = existing.createdAt;
        this.venue = existing.venue;
        this.organiser = existing.organiser;
        this.faculty = existing.faculty;
        this.tickets = existing.tickets;
    }

    public static class Builder {
        private String title;
        private String description;
        private LocalDateTime eventDate;
        private Integer capacity;
        private Boolean open;
        private LocalDateTime createdAt;
        private Venue venue;
        private Organiser organiser;
        private Faculty faculty;
        private Set<Ticket> tickets = new HashSet<>();
        private Set<PromoCode> promoCodes = new HashSet<>();

        public Builder setTitle(String title) { this.title = title; return this; }
        public Builder setDescription(String description) { this.description = description; return this; }
        public Builder setEventDate(LocalDateTime eventDate) { this.eventDate = eventDate; return this; }
        public Builder setCapacity(Integer capacity) { this.capacity = capacity; return this; }
        public Builder setOpen(Boolean open) { this.open = open; return this; }
        public Builder setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder setVenue(Venue venue) { this.venue = venue; return this; }
        public Builder setOrganiser(Organiser organiser) { this.organiser = organiser; return this; }
        public Builder setFaculty(Faculty faculty) { this.faculty = faculty; return this; }
        public Builder setTickets(Set<Ticket> tickets) { this.tickets = tickets; return this; }
        public Builder setPromoCodes(Set<PromoCode> promoCodes) { this.promoCodes = promoCodes; return this; }
        public Event build() { return new Event(this); }
        public ErrorResponse.Builder id(Long id) { return null; }
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getEventDate() { return eventDate; }
    public Integer getCapacity() { return capacity; }
    public Boolean isOpen() { return open; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Venue getVenue() { return venue; }
    public Organiser getOrganiser() { return organiser; }
    public Faculty getFaculty() { return faculty; }
    public Set<Ticket> getTickets() { return tickets; }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", eventDate=" + eventDate +
                ", capacity=" + capacity +
                ", open=" + open +
                ", createdAt=" + createdAt +
                ", venue=" + (venue != null ? venue.getName() : "null") +
                ", organiser=" + (organiser != null ? organiser.getFirstName() : "null") +
                ", faculty=" + (faculty != null ? faculty.getName() : "null") +
                ", tickets=" + tickets.size() +
                '}';
    }
}
