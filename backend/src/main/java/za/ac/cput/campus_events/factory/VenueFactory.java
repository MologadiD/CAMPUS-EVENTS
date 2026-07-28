package za.ac.cput.campus_events.factory;

import za.ac.cput.campus_events.domain.Address;
import za.ac.cput.campus_events.domain.Venue;

public class VenueFactory {

    public static Venue createVenue(String name, Integer capacity, Address address) {

        if (name == null || name.length() < 3) {
            return null;
        }

        if (capacity == null || capacity <= 0) {
            // TODO : set a sensible maximum capacity
            return null;
        }

        if (address == null) {
            // TODO : verify the address exists or is valid
            return null;
        }

        return new Venue.Builder()
                .setName(name)
                .setCapacity(capacity)
                .setAddress(address)
                .build();
    }
}