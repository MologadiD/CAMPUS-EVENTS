package za.ac.cput.campus_events.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Event {

    private String title;
    private String description;
    private LocalDateTime eventDate;
    private Integer capacity;
    private Boolean open;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "venueId", nullable = false)
    private Venue venue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organiser_id")
    private Organiser organiser;

    // Private constructor for Builder
    public Event(Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.eventDate = builder.eventDate;
        this.capacity = builder.capacity;
        this.open = builder.open;
        this.createdAt = builder.createdAt;
        this.venue = builder.venue;
    }

    public Event() {

    }

    // Builder Pattern
    public static class Builder {
        private String title;
        private String description;
        private LocalDateTime eventDate;
        private Integer capacity;
        private Boolean open;
        private LocalDateTime createdAt;
        private Venue venue;

        public Builder setTitle(String title) { this.title = title; return this; }
        public Builder setDescription(String description) { this.description = description; return this; }
        public Builder setEventDate(LocalDateTime eventDate) { this.eventDate = eventDate; return this; }
        public Builder setCapacity(Integer capacity) { this.capacity = capacity; return this; }
        public Builder setOpen(Boolean open) { this.open = open; return this; }
        public Builder setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder setVenue(Venue venue) { this.venue = venue; return this; }

        public Event build() { return new Event(this); }
    }

    // Getters
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
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", eventDate=" + eventDate +
                ", capacity=" + capacity +
                ", open=" + open +
                ", createdAt=" + createdAt +
                ", venue=" + (venue != null ? venue.getName() : "null") +
                '}';
    }
}

