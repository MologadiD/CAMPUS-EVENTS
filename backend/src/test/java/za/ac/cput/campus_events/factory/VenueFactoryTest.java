package za.ac.cput.campus_events.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.campus_events.domain.Address;
import za.ac.cput.campus_events.domain.Venue;

import static org.junit.jupiter.api.Assertions.*;

class VenueFactoryTest {

    private final Address address = new Address.Builder()
            .setStreet("Main Road")
            .setSuburb("Goodwood")
            .setCity("Cape Town")
            .setProvince("Western Cape")
            .setPostalCode("8000")
            .build();

    @Test
    void createVenueSuccess() {

        Venue venue = VenueFactory.createVenue(
                "Sports Hall",
                500,
                address
        );

        assertNotNull(venue);
        assertEquals("Sports Hall", venue.getName());
        assertEquals(500, venue.getCapacity());
        assertEquals(address, venue.getAddress());
    }

    @Test
    void createVenueNullName() {

        Venue venue = VenueFactory.createVenue(
                null,
                500,
                address
        );

        assertNull(venue);
    }

    @Test
    void createVenueBlankName() {

        Venue venue = VenueFactory.createVenue(
                "   ",
                500,
                address
        );

        assertNull(venue);
    }

    @Test
    void createVenueShortName() {

        Venue venue = VenueFactory.createVenue(
                "AB",
                500,
                address
        );

        assertNull(venue);
    }

    @Test
    void createVenueNullCapacity() {

        Venue venue = VenueFactory.createVenue(
                "Sports Hall",
                null,
                address
        );

        assertNull(venue);
    }

    @Test
    void createVenueZeroCapacity() {

        Venue venue = VenueFactory.createVenue(
                "Sports Hall",
                0,
                address
        );

        assertNull(venue);
    }

    @Test
    void createVenueNegativeCapacity() {

        Venue venue = VenueFactory.createVenue(
                "Sports Hall",
                -50,
                address
        );

        assertNull(venue);
    }

    @Test
    void createVenueExcessiveCapacity() {

        Venue venue = VenueFactory.createVenue(
                "Sports Hall",
                200000,
                address
        );

        assertNull(venue);
    }

    @Test
    void createVenueNullAddress() {

        Venue venue = VenueFactory.createVenue(
                "Sports Hall",
                500,
                null
        );

        assertNull(venue);
    }
}