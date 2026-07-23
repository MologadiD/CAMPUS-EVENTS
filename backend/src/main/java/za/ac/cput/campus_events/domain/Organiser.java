package za.ac.cput.campus_events.domain;
/*
Dikgale Mologadi
Student No: 231016263
 */

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Organiser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "organiser", fetch = FetchType.LAZY)
    private List<Event> events;

    protected Organiser() {}

    private Organiser(Builder builder) {
        this.id        = builder.id;
        this.firstName = builder.firstName;
        this.lastName  = builder.lastName;
        this.email     = builder.email;
        this.role      = builder.role;
        this.createdAt = builder.createdAt;
        this.events    = builder.events;
    }

    public Long          getId()        { return id; }
    public String        getFirstName() { return firstName; }
    public String        getLastName()  { return lastName; }
    public String        getEmail()     { return email; }
    public String        getRole()      { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<Event>   getEvents()    { return events; }

    public void createEvent() {
        // business logic for creating an event
    }

    public void updateEvent(Event event) {
        // business logic for updating an event
    }

    public void closeEvent(Event event) {
        // business logic for closing an event
    }

    @Override
    public String toString() {
        return "Organiser{" +
                "id="           + id          +
                ", firstName='" + firstName   + '\'' +
                ", lastName='"  + lastName    + '\'' +
                ", email='"     + email       + '\'' +
                ", role='"      + role        + '\'' +
                ", createdAt="  + createdAt   +
                '}';
    }

    public static class Builder {
        private Long          id;
        private String        firstName;
        private String        lastName;
        private String        email;
        private String        role;
        private LocalDateTime createdAt;
        private List<Event>   events;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Builder role(String role) {
            this.role = role;
            return this;
        }
        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public Builder events(List<Event> events) {
            this.events = events;
            return this;
        }

        public Organiser build() {
            if (Helper.isNullOrEmpty(firstName)) return null;
            if (Helper.isNullOrEmpty(lastName))  return null;
            if (!Helper.isValidEmail(email))     return null;
            if (Helper.isNullOrEmpty(role))      return null;
            if (createdAt == null) createdAt = LocalDateTime.now();
            return new Organiser(this);
        }
    }

}
