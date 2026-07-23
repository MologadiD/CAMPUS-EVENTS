package za.ac.cput.campus_events.model;

import jakarta.persistence.*;

import java.util.Date;


@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;
    // when we have all entities this must become a ManyToOne relationship
    private Long studentId; // this one too
    private Long eventId; // this one too
    private Long promoCodeId; // this one too
    private Date createdAt;

    public Ticket() {

    }

    public Ticket(Builder builder) {
        // no Id since its Auto-generated
        this.price = builder.price;
        this.studentId = builder.studentId;
        this.eventId = builder.eventId;
        this.promoCodeId = builder.promoCodeId;
        this.createdAt = builder.createdAt;
    }

    public Long getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getEventId() {
        return eventId;
    }

    public Long getPromoCodeId() {
        return promoCodeId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public static class Builder {
        private Long id;
        private Double price;
        private Long studentId;
        private Long eventId;
        private Long promoCodeId;
        private Date createdAt;

        public Builder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public Builder setStudentId(Long studentId) {
            this.studentId = studentId;
            return this;
        }

        public Builder setEventId(Long eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder setPromoCodeId(Long promoCodeId) {
            this.promoCodeId = promoCodeId;
            return this;
        }

        public Builder setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Ticket build() {
            return new Ticket(this);
        }
    }



}
