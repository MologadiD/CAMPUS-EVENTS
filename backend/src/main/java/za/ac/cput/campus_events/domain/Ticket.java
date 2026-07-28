package za.ac.cput.campus_events.domain;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "promo_code_id")
    private PromoCode promoCode;

    private Date createdAt;

    public Ticket() {
    }

    public Ticket(Builder builder) {
        this.price = builder.price;
        this.student = builder.student;
        this.event = builder.event;
        this.promoCode = builder.promoCode;
        this.createdAt = builder.createdAt;
    }



    public Long getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public Student getStudent() {
        return student;
    }

    public Event getEvent() {
        return event;
    }

    public PromoCode getPromoCode() {
        return promoCode;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public static class Builder {

        private Double price;
        private Student student;
        private Event event;
        private PromoCode promoCode;
        private Date createdAt;

        public Builder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public Builder setStudent(Student student) {
            this.student = student;
            return this;
        }

        public Builder setEvent(Event event) {
            this.event = event;
            return this;
        }

        public Builder setPromoCode(PromoCode promoCode) {
            this.promoCode = promoCode;
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

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", price=" + price +
                ", student=" + student +
                ", event=" + event +
                ", promoCode=" + promoCode +
                ", createdAt=" + createdAt +
                '}';
    }
}