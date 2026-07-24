package za.ac.cput.campus_events.domain;

import jakarta.persistence.*;

@Entity
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer capacity;

    @Embedded
    private Address address;

    private Venue(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.capacity = builder.capacity;
        this.address = builder.address;
    }

    public static class Builder {
        private Long id;
        private String name;
        private Integer capacity;
        private Address address;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder capacity(Integer capacity) { this.capacity = capacity; return this; }
        public Builder address(Address address) { this.address = address; return this; }

        public Venue build() { return new Venue(this); }
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Integer getCapacity() { return capacity; }
    public Address getAddress() { return address; }

    @Override
    public String toString() {
        return "Venue{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", address=" + address +
                '}';
    }
}

