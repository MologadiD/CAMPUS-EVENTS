package za.ac.cput.campus_events.factory;
/*
Mologadi Dikgale
Student no: 231016263
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.campus_events.domain.Faculty;

import static org.junit.jupiter.api.Assertions.*;

public class FacultyFactoryTest {
    private FacultyFactory facultyFactory;

    @BeforeEach
    void setUp() {
        facultyFactory = new FacultyFactory();
    }

    @Test
    void testCreateFaculty_ValidInputs_ShouldReturnFaculty() {
        Faculty faculty = facultyFactory.createFaculty(
                "Faculty of Engineering",
                "ACTIVE",
                "engineering@cput.ac.za",
                1L);
        assertNotNull(faculty);
        assertEquals("Faculty of Engineering", faculty.getName());
        assertEquals("ACTIVE",                 faculty.getStatus());
        assertEquals("engineering@cput.ac.za", faculty.getContactEmail());
        assertEquals(1L,                        faculty.getCreatedByAdminId());
    }

    @Test
    void testCreateFaculty_NullName_ShouldReturnNull() {
        Faculty faculty = facultyFactory.createFaculty(
                null,
                "ACTIVE",
                "engineering@cput.ac.za",
                1L);
        assertNull(faculty);
    }

    @Test
    void testCreateFaculty_EmptyName_ShouldReturnNull() {
        Faculty faculty = facultyFactory.createFaculty(
                "",
                "ACTIVE",
                "engineering@cput.ac.za",
                1L);
        assertNull(faculty);
    }

    @Test
    void testCreateFaculty_NullStatus_ShouldReturnNull() {
        Faculty faculty = facultyFactory.createFaculty(
                "Faculty of Engineering",
                null,
                "engineering@cput.ac.za",
                1L);
        assertNull(faculty);
    }

    @Test
    void testCreateFaculty_EmptyStatus_ShouldReturnNull() {
        Faculty faculty = facultyFactory.createFaculty(
                "Faculty of Engineering",
                "",
                "engineering@cput.ac.za",
                1L);
        assertNull(faculty);
    }

    @Test
    void testCreateFaculty_InvalidEmail_ShouldReturnNull() {
        Faculty faculty = facultyFactory.createFaculty(
                "Faculty of Engineering",
                "ACTIVE",
                "invalidemail",
                1L);
        assertNull(faculty);
    }

    @Test
    void testCreateFaculty_NullEmail_ShouldReturnNull() {
        Faculty faculty = facultyFactory.createFaculty(
                "Faculty of Engineering",
                "ACTIVE",
                null,
                1L);
        assertNull(faculty);
    }

    @Test
    void testCreateFaculty_NullAdminId_ShouldReturnNull() {
        Faculty faculty = facultyFactory.createFaculty(
                "Faculty of Engineering",
                "ACTIVE",
                "engineering@cput.ac.za",
                null);
        assertNull(faculty);
    }

    @Test
    void testCreateFaculty_CreatedAtShouldBeSetAutomatically() {
        Faculty faculty = facultyFactory.createFaculty(
                "Faculty of Engineering",
                "ACTIVE",
                "engineering@cput.ac.za",
                1L);
        assertNotNull(faculty);
        assertNotNull(faculty.getCreatedAt());
    }
}
