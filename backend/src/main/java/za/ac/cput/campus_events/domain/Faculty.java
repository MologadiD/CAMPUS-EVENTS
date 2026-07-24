package za.ac.cput.campus_events.domain;
import jakarta.persistence.*;
import za.ac.cput.campus_events.util.Helper;

import java.time.LocalDateTime;
/*
Mologadi Dikagle
student no:231016263
 */

@Entity

public class Faculty {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String status;
        private String contactEmail;
        private Long createdByAdminId;
        private LocalDateTime createdAt;

        protected Faculty() {

        }

        private Faculty(Builder builder) {
            this.id = builder.id;
            this.name = builder.name;
            this.status = builder.status;
            this.contactEmail = builder.contactEmail;
            this.createdByAdminId = builder.createdByAdminId;
            this.createdAt = builder.createdAt;
        }

        public Long  getId(){
            return id;
        }
        public String getName() {
            return name;
        }
        public String getStatus() {
            return status;
        }
        public String getContactEmail() {
            return contactEmail;
        }
        public Long getCreatedByAdminId() {
            return createdByAdminId;
        }
        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        @Override
        public String toString() {
            return "Faculty{" +
                    "id="                + id               +
                    ", name='"           + name             + '\'' +
                    ", status='"         + status           + '\'' +
                    ", contactEmail='"   + contactEmail     + '\'' +
                    ", createdByAdminId="+ createdByAdminId +
                    ", createdAt="       + createdAt        +
                    '}';
        }

        public static class Builder {
            private Long id;
            private String name;
            private String status;
            private String contactEmail;
            private Long createdByAdminId;
            private LocalDateTime createdAt;

            public Builder id(Long id) {
                this.id = id;
                return this;
            }
            public Builder name(String name) {
                this.name = name;
                return this;
            }
            public Builder status(String status) {
                this.status = status;
                return this;
            }
            public Builder contactEmail(String contactEmail) {
                this.contactEmail = contactEmail;
                return this;
            }
            public Builder createdByAdminId(Long createdByAdminId) {
                this.createdByAdminId = createdByAdminId;
                return this;
            }
            public Builder createdAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
                return this;
            }
            public Faculty build() {
                if (Helper.isNullOrEmpty(name))          return null;
                if (Helper.isNullOrEmpty(status))        return null;
                if (!Helper.isValidEmail(contactEmail))  return null;
                if (createdByAdminId == null)            return null;
                if (createdAt == null) createdAt = LocalDateTime.now();
                return new Faculty(this);
            }
        }
    }
