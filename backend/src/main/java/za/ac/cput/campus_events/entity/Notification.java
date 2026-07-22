package za.ac.cput.campus_events.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    private String notificationId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String message;

    @Column(nullable = false)
    private Boolean read;

    @Column(nullable = false)
    private LocalDateTime createdAt;
/*
we'll activate them once we have the Student entity
    //@ManyToOne
    //@JoinColumn(name = "student_id")
    //private Student student;
*/
    protected Notification() {
    }

    private Notification(Builder builder) {
        this.notificationId = builder.notificationId;
        this.title = builder.title;
        this.message = builder.message;
        this.read = builder.read;
        this.createdAt = builder.createdAt;
      //  this.student = builder.student;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getRead() {
        return read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
/*
    public Student getStudent() {
        return student;
    }
*/
    public void markAsRead() {
        this.read = true;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId='" + notificationId + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", read=" + read +
                ", createdAt=" + createdAt +
                '}';
    }

    public static class Builder {

        private String notificationId;
        private String title;
        private String message;
        private Boolean read;
        private LocalDateTime createdAt;
       // private Student student;

        public Builder setNotificationId(String notificationId) {
            this.notificationId = notificationId;
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
            this.notificationId = notification.notificationId;
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