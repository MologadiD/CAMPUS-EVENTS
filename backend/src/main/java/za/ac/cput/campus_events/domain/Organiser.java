package za.ac.cput.campus_events.domain;
/*
Dikgale Mologadi
Student No: 231016263
 */

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        // we'll create an addEvent method so for now just init
        this.events = new ArrayList<>();
    }

    public Long          getId()        { return id; }
    public String        getFirstName() { return firstName; }
    public String        getLastName()  { return lastName; }
    public String        getEmail()     { return email; }
    public String        getRole()      { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<Event>   getEvents()    { return events; }

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


        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder setRole(String role) {
            this.role = role;
            return this;
        }
        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Organiser build() {
            return new Organiser(this);
        }
    }

}
