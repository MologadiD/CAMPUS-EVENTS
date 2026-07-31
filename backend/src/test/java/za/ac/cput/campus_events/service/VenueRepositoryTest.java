package za.ac.cput.campus_events.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import za.ac.cput.campus_events.domain.Address;
import za.ac.cput.campus_events.domain.Venue;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class VenueRepositoryTest {

    @Autowired
    private VenueRepository venueRepository;

    @Test
    void testSaveAndFindByName() {
        Address address = new Address.Builder()
                .setStreet("123 Adderley St")
                .setCity("Cape Town")
                .setProvince("Western Cape")
                .setPostalCode("8000")
                .build();

        Venue v = new Venue.Builder()
                .setName("Hall B")
                .setCapacity(150)
                .setAddress(address)
                .build();

        venueRepository.save(v);

        List<Venue> result = venueRepository.findByName("Hall B");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Hall B", result.get(0).getName());
    }

    @Test
    void testFindByCapacityGreaterThan() {
        Address address = new Address.Builder()
                .setStreet("10 Plein St")
                .setCity("Cape Town")
                .setProvince("Western Cape")
                .setPostalCode("8001")
                .build();

        Venue small = new Venue.Builder()
                .setName("Small Room")
                .setCapacity(50)
                .setAddress(address)
                .build();
        venueRepository.save(small);

        Venue large = new Venue.Builder()
                .setName("Large Hall")
                .setCapacity(300)
                .setAddress(address)
                .build();
        venueRepository.save(large);

        List<Venue> result = venueRepository.findByCapacityGreaterThan(100);
        assertTrue(result.stream().anyMatch(v -> "Large Hall".equals(v.getName())));
    }

    @Test
    void testFindByAddressCity() {
        Address address = new Address.Builder()
                .setStreet("78 Market St")
                .setCity("Cape Town")
                .setProvince("Western Cape")
                .setPostalCode("8002")
                .build();

        Venue v = new Venue.Builder()
                .setName("Studio")
                .setCapacity(80)
                .setAddress(address)
                .build();
        venueRepository.save(v);

        List<Venue> result = venueRepository.findByAddress_City("Cape Town");
        assertFalse(result.isEmpty());
        assertEquals("Cape Town", result.get(0).getAddress().getCity());
    }
}
