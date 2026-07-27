package za.ac.cput.campus_events.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    private String Id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String message;

    @Column(name = "is_read")
    private boolean read;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    protected Notification() {
    }

    private Notification(Builder builder) {
        this.Id = builder.Id;
        this.title = builder.title;
        this.message = builder.message;
        this.read = builder.read;
        this.createdAt = builder.createdAt;
      //  this.student = builder.student;
    }

    public String getId() {
        return Id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public boolean getRead() {
        return read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Student getStudent() {
        return student;
    }

    public void markAsRead() {
        this.read = true;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId='" + Id + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", read=" + read +
                ", createdAt=" + createdAt +
                '}';
    }

    public static class Builder {

        private String Id;
        private String title;
        private String message;
        private boolean read;
        private LocalDateTime createdAt;
       // private Student student;

        public Builder setId(String Id) {
            this.Id = Id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setRead(Boolean read) {
            this.read = read;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
/*
        public Builder setStudent(Student student) {
            this.student = student;
            return this;
        }
*/
        public Builder copy(Notification notification) {
            this.Id = notification.Id;
            this.title = notification.title;
            this.message = notification.message;
            this.read = notification.read;
            this.createdAt = notification.createdAt;
            //this.student = notification.student;
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
    }
}