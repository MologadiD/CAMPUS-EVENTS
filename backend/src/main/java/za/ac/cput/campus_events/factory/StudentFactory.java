package za.ac.cput.campus_events.factory;

import za.ac.cput.campus_events.domain.Faculty;
import za.ac.cput.campus_events.domain.Student;
import za.ac.cput.campus_events.util.EmailValidator;

public class StudentFactory {

    public static Student createStudent(Faculty faculty, String firstName, String lastName,
                                        String email, String studentNumber){

        if(firstName == null || firstName.length() < 3){
            return null;
        }

        if(lastName == null || lastName.length() < 3){
            return null;
        }

        if(email == null || email.length() < 3){
            return null;
        }

        if(!EmailValidator.isValid(email)){
            // TODO : does the student number make up a part of the email?
            return null;
        }

        if(faculty == null){
            // TODO : check if the database has the faculty
            return null;
        }

        if(studentNumber == null || studentNumber.length() < 10 ){
            // TODO: CUSTOM checker for student number here later
            return null;
        }

        return new Student.Builder()
                .setFirstName(firstName)
                .setEmail(email)
                .setStudentNumber(studentNumber)
                .setFaculty(faculty)
                .setLastName(lastName)
                .build();
    }
}
