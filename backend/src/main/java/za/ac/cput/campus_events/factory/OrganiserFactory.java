package za.ac.cput.campus_events.factory;
/*
Mologadi  Dikgale
Student No: 231016263
 */

import za.ac.cput.campus_events.domain.Organiser;
import java.time.LocalDateTime;

public class OrganiserFactory {
    public static Organiser createOrganiser(String firstName, String lastName, String email, String role) {
        return new Organiser.Builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .role(role)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
