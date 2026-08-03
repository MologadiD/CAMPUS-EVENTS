package za.ac.cput.campus_events.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.campus_events.DTO.RegisterRequestDTO;
import za.ac.cput.campus_events.DTO.RegisterResponseDTO;
import za.ac.cput.campus_events.DTO.ResendRequestDTO;
import za.ac.cput.campus_events.DTO.VerifyRequestDTO;
import za.ac.cput.campus_events.DTO.VerifyResponseDTO;
import za.ac.cput.campus_events.domain.PendingRegistration;
import za.ac.cput.campus_events.repository.OrganiserRepository;
import za.ac.cput.campus_events.repository.PendingRegistrationRepository;
import za.ac.cput.campus_events.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthService {

    private final PendingRegistrationRepository pendingRegistrationRepository;
    private final StudentRepository studentRepository;
    private final OrganiserRepository organiserRepository;

    @Autowired
    public AuthService(PendingRegistrationRepository pendingRegistrationRepository,
                       StudentRepository studentRepository,
                       OrganiserRepository organiserRepository) {

        this.pendingRegistrationRepository = pendingRegistrationRepository;
        this.studentRepository = studentRepository;
        this.organiserRepository = organiserRepository;
    }

    /**
     * Step 1:
     * Accept registration details.
     * Generate a PIN.
     * Save as PendingRegistration.
     */
    public RegisterResponseDTO register(RegisterRequestDTO request) {

        RegisterResponseDTO response = new RegisterResponseDTO();

        if (request == null
                || request.getEmail() == null || request.getEmail().isBlank()
                || request.getPassword() == null || request.getPassword().isBlank()
                || request.getRole() == null || request.getRole().isBlank()) {

            response.setSuccess(false);
            response.setMessage("Invalid registration details.");
            return response;
        }

        String pin = generatePin();

        PendingRegistration pendingRegistration =
                new PendingRegistration.Builder()
                        .setEmail(request.getEmail())
                        .setPassword(request.getPassword())
                        .setRole(request.getRole())
                        .setFacultyId(request.getFacultyId())
                        .setStudentNumber(request.getStudentNumber())
                        .setPin(pin)
                        .build();

        pendingRegistrationRepository.save(pendingRegistration);

        response.setSuccess(true);
        response.setMessage("Registration successful. Please verify your account.");
        response.setUuid(pendingRegistration.getUuid());
        response.setEmail(pendingRegistration.getEmail());

        // DEV MODE ONLY - Later On
        response.setPin(pin);

        return response;
    }

    /**
     * Step 2:
     * Verify PIN.
     */
    public VerifyResponseDTO verify(VerifyRequestDTO request) {

        VerifyResponseDTO response = new VerifyResponseDTO();

        if (request == null
                || request.getUuid() == null || request.getUuid().isBlank()
                || request.getPin() == null || request.getPin().isBlank()) {

            response.setSuccess(false);
            response.setMessage("Invalid verification request.");
            return response;
        }

        PendingRegistration pendingRegistration =
                pendingRegistrationRepository
                        .findById(request.getUuid())
                        .orElse(null);

        if (pendingRegistration == null) {
            response.setSuccess(false);
            response.setMessage("Registration request not found.");
            return response;
        }

        if (pendingRegistration.isExpired()) {
            response.setSuccess(false);
            response.setMessage("Verification PIN has expired.");
            return response;
        }

        if (!pendingRegistration.getPin().equals(request.getPin())) {
            response.setSuccess(false);
            response.setMessage("Incorrect PIN.");
            return response;
        }

        /*
         * TODO
         * Create Student or Organiser account here.
         */

        pendingRegistrationRepository.delete(pendingRegistration);

        response.setSuccess(true);
        response.setMessage("Account verified successfully.");
        response.setRole(pendingRegistration.getRole());

        return response;
    }

    /**
     * Step 3:
     * Generate a new PIN.
     */
    public RegisterResponseDTO resend(ResendRequestDTO request) {

        RegisterResponseDTO response = new RegisterResponseDTO();

        if (request == null
                || request.getUuid() == null
                || request.getUuid().isBlank()) {

            response.setSuccess(false);
            response.setMessage("Invalid resend request.");
            return response;
        }

        PendingRegistration pendingRegistration =
                pendingRegistrationRepository
                        .findById(request.getUuid())
                        .orElse(null);

        if (pendingRegistration == null) {
            response.setSuccess(false);
            response.setMessage("Registration request not found.");
            return response;
        }

        String newPin = generatePin();

        // Update the existing registration
        pendingRegistration.setPin(newPin);
        pendingRegistration.setExpiresAt(LocalDateTime.now().plusMinutes(10));

        pendingRegistrationRepository.save(pendingRegistration);

        response.setSuccess(true);
        response.setMessage("New PIN generated.");
        response.setUuid(pendingRegistration.getUuid());
        response.setEmail(pendingRegistration.getEmail());

        // DEV MODE ONLY
        response.setPin(newPin);

        return response;
    }

    private String generatePin() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }
}