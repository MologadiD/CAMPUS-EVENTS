package za.ac.cput.campus_events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.campus_events.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
