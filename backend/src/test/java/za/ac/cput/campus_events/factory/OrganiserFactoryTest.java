package za.ac.cput.campus_events.factory;
/*
Mologadi Dikgale
Student no: 231016263
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.campus_events.domain.Organiser;

import static org.junit.jupiter.api.Assertions.*;


public class OrganiserFactoryTest {

    private OrganiserFactory organiserFactory;

    @BeforeEach
    void setUp() {
        organiserFactory = new OrganiserFactory();
    }

    @Test
    void testCreateOrganiser_ValidInputs_ShouldReturnOrganiser() {
        Organiser organiser = organiserFactory.createOrganiser(
                "John",
                "Smith",
                "john@cput.ac.za",
                "EVENT_COORDINATOR");
        assertNotNull(organiser);
        assertEquals("John",              organiser.getFirstName());
        assertEquals("Smith",             organiser.getLastName());
        assertEquals("john@cput.ac.za",   organiser.getEmail());
        assertEquals("EVENT_COORDINATOR", organiser.getRole());
    }

    @Test
    void testCreateOrganiser_NullFirstName_ShouldReturnNull() {
        Organiser organiser = organiserFactory.createOrganiser(
                null,
                "Smith",
                "john@cput.ac.za",
                "EVENT_COORDINATOR");
        assertNull(organiser);
    }

    @Test
    void testCreateOrganiser_EmptyFirstName_ShouldReturnNull() {
        Organiser organiser = organiserFactory.createOrganiser(
                "",
                "Smith",
                "john@cput.ac.za",
                "EVENT_COORDINATOR");
        assertNull(organiser);
    }

    @Test
    void testCreateOrganiser_NullLastName_ShouldReturnNull() {
        Organiser organiser = organiserFactory.createOrganiser(
                "John",
                null,
                "john@cput.ac.za",
                "EVENT_COORDINATOR");
        assertNull(organiser);
    }

    @Test
    void testCreateOrganiser_EmptyLastName_ShouldReturnNull() {
        Organiser organiser = organiserFactory.createOrganiser(
                "John",
                "",
                "john@cput.ac.za",
                "EVENT_COORDINATOR");
        assertNull(organiser);
    }

    @Test
    void testCreateOrganiser_InvalidEmail_ShouldReturnNull() {
        Organiser organiser = organiserFactory.createOrganiser(
                "John",
                "Smith",
                "invalidemail",
                "EVENT_COORDINATOR");
        assertNull(organiser);
    }

    @Test
    void testCreateOrganiser_NullEmail_ShouldReturnNull() {
        Organiser organiser = organiserFactory.createOrganiser(
                "John",
                "Smith",
                null,
                "EVENT_COORDINATOR");
        assertNull(organiser);
    }

    @Test
    void testCreateOrganiser_NullRole_ShouldReturnNull() {
        Organiser organiser = organiserFactory.createOrganiser(
                "John",
                "Smith",
                "john@cput.ac.za",
                null);
        assertNull(organiser);
    }

    @Test
    void testCreateOrganiser_CreatedAtShouldBeSetAutomatically() {
        Organiser organiser = organiserFactory.createOrganiser(
                "John",
                "Smith",
                "john@cput.ac.za",
                "EVENT_COORDINATOR");
        assertNotNull(organiser);
        assertNotNull(organiser.getCreatedAt());
    }
}
