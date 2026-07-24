package za.ac.cput.campus_events.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

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
    @JoinColumn(name = "venueId", nullable = false)
    private Venue venue;

    // Private constructor for Builder
    private Event(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.eventDate = builder.eventDate;
        this.capacity = builder.capacity;
        this.open = builder.open;
        this.createdAt = builder.createdAt;
        this.venue = builder.venue;
    }

    // Builder Pattern
    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private LocalDateTime eventDate;
        private Integer capacity;
        private Boolean open;
        private LocalDateTime createdAt;
        private Venue venue;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder eventDate(LocalDateTime eventDate) { this.eventDate = eventDate; return this; }
        public Builder capacity(Integer capacity) { this.capacity = capacity; return this; }
        public Builder open(Boolean open) { this.open = open; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder venue(Venue venue) { this.venue = venue; return this; }

        public Event build() { return new Event(this); }
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getEventDate() { return eventDate; }
    public Integer getCapacity() { return capacity; }
    public Boolean isOpen() { return open; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Venue getVenue() { return venue; }

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
                '}';
    }
}
