import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "registrations")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime registrationAt;
    private LocalDateTime cancelledAt;

    public Registration(Builder builder) {
        this.registrationAt = builder.registrationAt;
        this.cancelledAt = builder.cancelledAt;
    }

    public static class Builder {
        private LocalDateTime registrationAt;
        private LocalDateTime cancelledAt;

        public Builder registrationAt(LocalDateTime registrationAt) {
            this.registrationAt = registrationAt;
            return this;
        }

        public Builder cancelledAt(LocalDateTime cancelledAt) {
            this.cancelledAt = cancelledAt;
            return this;
        }

        public Registration build() {
            return new Registration(this);
        }
    }


}
