package za.ac.cput.campus_events.factory;
/*
Mologadi Dikgale
Student No: 231016263
 */
import za.ac.cput.campus_events.domain.Faculty;
import java.time.LocalDateTime;

public class FacultyFactory {
    public Faculty createFaculty(String name, String status,
                                 String contactEmail,
                                 Long createdByAdminId) {
        return new Faculty.Builder()
                .name(name)
                .status(status)
                .contactEmail(contactEmail)
                .createdByAdminId(createdByAdminId)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
