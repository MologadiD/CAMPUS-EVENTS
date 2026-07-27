package za.ac.cput.campus_events.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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

    private String faculty ; // link this with faculty class later

    protected Student(Builder builder){
        this.faculty = builder.faculty;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.studentNumber = builder.studentNumber;

    }

    public Student() {

    }

    public static class Builder {

        private String firstName;
        private String lastName;
        private String email;
        private String studentNumber;
        private String faculty;

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

        public Builder setFaculty(String faculty) {
            this.faculty = faculty;
            return this;

        }

        public Student build(){
            return new Student(this);
        }

    }



}
