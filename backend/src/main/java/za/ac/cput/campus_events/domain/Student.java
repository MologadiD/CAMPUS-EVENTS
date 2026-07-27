package za.ac.cput.campus_events.domain;

import jakarta.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String studentNumber; // some cool validation for this later

    private boolean isVerified = false; // we dont want to expose this to the builder, only the Admin

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty; // link this with faculty class later

    protected Student(Builder builder) {
        this.faculty = builder.faculty;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.studentNumber = builder.studentNumber;
    }

    public Student() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public static class Builder {

        private String firstName;
        private String lastName;
        private String email;
        private String studentNumber;
        private Faculty faculty;

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setStudentNumber(String studentNumber) {
            this.studentNumber = studentNumber;
            return this;
        }

        public Builder setFaculty(Faculty faculty) {
            this.faculty = faculty;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", isVerified=" + isVerified +
                ", faculty=" + faculty +
                '}';
    }
}