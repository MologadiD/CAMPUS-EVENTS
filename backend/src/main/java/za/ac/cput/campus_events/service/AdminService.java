package za.ac.cput.campus_events.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import za.ac.cput.campus_events.DTO.CreateAdminResponseDTO;
import za.ac.cput.campus_events.DTO.CreateAdminRequestDTO;
import za.ac.cput.campus_events.domain.Admin;
import za.ac.cput.campus_events.domain.Organiser;
import za.ac.cput.campus_events.domain.PendingRegistration;
import za.ac.cput.campus_events.domain.Student;
import za.ac.cput.campus_events.factory.AdminFactory;
import za.ac.cput.campus_events.repository.AdminRepository;
import za.ac.cput.campus_events.repository.OrganiserRepository;
import za.ac.cput.campus_events.repository.PendingRegistrationRepository;

import java.time.LocalDateTime;

import za.ac.cput.campus_events.repository.StudentRepository;
import za.ac.cput.campus_events.service.EmailService;

import java.util.Optional;

@Service
public class AdminService implements IAdminService {

    private final AdminRepository adminRepository;
    private final PendingRegistrationRepository pendingRegistrationRepository;
    private final EmailService emailService;

    @Autowired
    public AdminService(AdminRepository adminRepository,
                        PendingRegistrationRepository pendingRegistrationRepository,
                        EmailService emailService) {
        this.adminRepository = adminRepository;
        this.pendingRegistrationRepository = pendingRegistrationRepository;
        this.emailService = emailService;
    }

    @Override
    public CreateAdminResponseDTO seedAdmin(CreateAdminRequestDTO request) {
        if (isSystemInitialized()) {
            return new CreateAdminResponseDTO(false, "System already has an admin", null);
        }

        if (pendingRegistrationRepository.existsByRoleIgnoreCase("ADMIN")) {
            return new CreateAdminResponseDTO(false, "An admin verification is already pending", null);
        }

        return createPendingAdmin(request, null);
    }

    @Override
    public boolean isSystemInitialized() {
        return adminRepository.count() > 0;
    }

    @Override
    public CreateAdminResponseDTO createAdmin(CreateAdminRequestDTO request, Long requestingAdminId) {
        if (requestingAdminId == null) {
            return new CreateAdminResponseDTO(false, "Admin only", null);
        }
        if (adminRepository.findById(requestingAdminId).isEmpty()) {
            return new CreateAdminResponseDTO(false, "Admin only", null);
        }

        return createPendingAdmin(request, requestingAdminId);
    }

    @Override
    public void changePassword(Long adminId, String currentPassword, String newPassword) {
        if (adminId == null || currentPassword == null || currentPassword.isBlank()
                || newPassword == null || newPassword.isBlank()) {
            throw new IllegalStateException("Invalid password change request");
        }

        Admin existing = adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalStateException("Admin not found"));

        if (!existing.getPassword().equals(currentPassword)) {
            throw new IllegalStateException("Incorrect current password");
        }

        adminRepository.save(existing.withPassword(newPassword));
    }

    @Override
    public Optional<Admin> authenticate(String email, String password) {
        return adminRepository.findByEmailAndPassword(email, password);
    }

    private CreateAdminResponseDTO createPendingAdmin(CreateAdminRequestDTO request, Long requestingAdminId) {
        if (request == null || request.getFirstName() == null || request.getFirstName().isBlank()
                || request.getLastName() == null || request.getLastName().isBlank()
                || request.getEmail() == null || request.getEmail().isBlank()
                || request.getPassword() == null || request.getPassword().isBlank()) {
            return new CreateAdminResponseDTO(false, "Invalid admin details", null);
        }

        if (adminRepository.existsByEmail(request.getEmail())) {
            return new CreateAdminResponseDTO(false, "An admin with this email already exists", null);
        }

        pendingRegistrationRepository.findByEmail(request.getEmail()).ifPresent(existing -> {
            if (existing.isExpired()) {
                pendingRegistrationRepository.delete(existing);
            }
        });

        if (pendingRegistrationRepository.findByEmail(request.getEmail()).isPresent()) {
            return new CreateAdminResponseDTO(false, "A verification request for this email already exists", null);
        }

        Admin admin = AdminFactory.createAdmin(request.getFirstName(),
                request.getLastName(), request.getEmail(), request.getPassword());

        if (admin == null) {
            return new CreateAdminResponseDTO(false, "Invalid admin details", null);
        }

        String pin = generatePin();

        PendingRegistration pendingRegistration = new PendingRegistration.Builder()
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setEmail(request.getEmail())
                .setPassword(request.getPassword())
                .setRole("ADMIN")
                .setPin(pin)
                .setExpiresAt(LocalDateTime.now().plusMinutes(10))
                .build();

        pendingRegistrationRepository.save(pendingRegistration);
        emailService.sendVerificationEmail(pendingRegistration.getEmail(), pin);

        return new CreateAdminResponseDTO(true,
                "Admin created. Check your email to verify the account.",
                null,
                pendingRegistration.getUuid());
    }
    //Ban
    @Override
    public void banUser(Long userId, Long requestingAdminId) {
        if (requestingAdminId == null)
            throw new IllegalStateException("Admin only");

        // For trying the ban
        StudentRepository.findById(userId).ifPresent(existing -> {
            Student banned = new Student.Builder()
                    .id(existing.getId())
                    .firstName(existing.getFirstName())
                    .lastName(existing.getLastName())
                    .email(existing.getEmail())
                    .password(existing.getPassword())
                    .studentNumber(existing.getStudentNumber())
                    .active(false)
                    .banned(true)
                    .build();
            StudentRepository.save(banned);
        });

        //
        OrganiserRepository.findById(userId).ifPresent(existing -> {
            Organiser banned = new Organiser(existing, false);
            OrganiserRepository.save(banned);
        });
    }

    private String generatePin() {
        return String.format("%06d", new java.util.Random().nextInt(1_000_000));
    }
}
