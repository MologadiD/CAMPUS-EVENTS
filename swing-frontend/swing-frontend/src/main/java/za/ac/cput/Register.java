package za.ac.cput;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formdev.flatlaf.FlatLightLaf;
import za.ac.cput.DTO.*;


public class Register extends JFrame {

    private final ButtonGroup roleGroup = new ButtonGroup();
    private JToggleButton btnRoleStudent;
    private JToggleButton btnRoleOrganiser;

    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtEmail;

    private JTextField txtStudentNumber;
    private JComboBox<String> cmbFacultyStudent;

    private JComboBox<String> cmbFacultyOrganiser;
    private JLabel lblPendingNotice;

    private JPasswordField pwdPassword;
    private JPasswordField pwdConfirm;
    private JButton btnRegister;
    private JButton btnGoLogin;

    private JPanel studentFieldsPanel;
    private JPanel organiserFieldsPanel;
    private JPanel conditionalWrapper;
    private CardLayout conditionalLayout;

    private static final Font LABEL_FONT = new Font("SansSerif", Font.PLAIN, 14);
    private static final Font FIELD_FONT = new Font("SansSerif", Font.PLAIN, 15);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 15);
    private static final Font HEADING_FONT = new Font("SansSerif", Font.BOLD, 24);
    private static final Font NOTICE_FONT = new Font("SansSerif", Font.ITALIC, 11);

    // label : input, one line — same numbers used for every row on this screen
    private static final int LABEL_WIDTH = 170;
    private static final int FIELD_WIDTH = 300;
    private static final int FIELD_HEIGHT = 38;
    private static final Dimension FIELD_SIZE = new Dimension(FIELD_WIDTH, FIELD_HEIGHT);
    private static final Dimension ROW_SIZE = new Dimension(LABEL_WIDTH + 10 + FIELD_WIDTH, FIELD_HEIGHT);

    public Register() {
        // set up the window — same size as Login
        setTitle("Campus Events - Register");
        setSize(960, 720);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // create the components
        btnRoleStudent = new JToggleButton("Student", true);
        btnRoleOrganiser = new JToggleButton("Organiser");
        roleGroup.add(btnRoleStudent);
        roleGroup.add(btnRoleOrganiser);

        Dimension roleBtnSize = new Dimension(150, 44);
        for (JToggleButton b : new JToggleButton[]{btnRoleStudent, btnRoleOrganiser}) {
            b.setPreferredSize(roleBtnSize);
            b.setFont(BUTTON_FONT);
            b.setFocusPainted(false);
        }

        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtEmail = new JTextField();
        txtStudentNumber = new JTextField();
        cmbFacultyStudent = new JComboBox<>(new String[]{"Select faculty..."});
        cmbFacultyOrganiser = new JComboBox<>(new String[]{"Select faculty..."});
        pwdPassword = new JPasswordField();
        pwdConfirm = new JPasswordField();

        // every field gets the exact same preferred AND maximum size — the maximum
        // is what actually matters, without it BoxLayout stretches a field to fill
        // whatever space is left over
        for (JTextField f : new JTextField[]{txtFirstName, txtLastName, txtEmail, txtStudentNumber, pwdPassword, pwdConfirm}) {
            f.setFont(FIELD_FONT);
            f.setPreferredSize(FIELD_SIZE);
            f.setMaximumSize(FIELD_SIZE);
        }
        for (JComboBox<String> c : new JComboBox[]{cmbFacultyStudent, cmbFacultyOrganiser}) {
            c.setFont(FIELD_FONT);
            c.setPreferredSize(FIELD_SIZE);
            c.setMaximumSize(FIELD_SIZE);
        }

        btnRegister = new JButton("Create account");
        btnGoLogin = new JButton("Already have an account? Sign in");
        btnRegister.setFont(BUTTON_FONT);
        btnGoLogin.setFont(LABEL_FONT);
        btnRegister.setPreferredSize(new Dimension(ROW_SIZE.width, 44));
        btnGoLogin.setPreferredSize(new Dimension(ROW_SIZE.width, 28));

        // create image panel
        JPanel purplePanel = new JPanel();
        purplePanel.setBackground(new Color(108, 61, 189));
        purplePanel.setPreferredSize(new Dimension(400, 720));

        // ---- conditional Student vs Organiser fields (CardLayout swaps them) ----
        studentFieldsPanel = buildStudentFieldsPanel();
        organiserFieldsPanel = buildOrganiserFieldsPanel();

        conditionalLayout = new CardLayout();
        conditionalWrapper = new JPanel(conditionalLayout);
        conditionalWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        conditionalWrapper.setMaximumSize(new Dimension(ROW_SIZE.width, 100));
        conditionalWrapper.add(studentFieldsPanel, "STUDENT");
        conditionalWrapper.add(organiserFieldsPanel, "ORGANISER");

        btnRoleStudent.addItemListener(e -> {
            if (btnRoleStudent.isSelected()) {
                conditionalLayout.show(conditionalWrapper, "STUDENT");
            }
        });
        btnRoleOrganiser.addItemListener(e -> {
            if (btnRoleOrganiser.isSelected()) {
                conditionalLayout.show(conditionalWrapper, "ORGANISER");
            }
        });

        // create form panel
        JPanel formSide = new JPanel();
        formSide.setLayout(new BoxLayout(formSide, BoxLayout.Y_AXIS));
        formSide.setBorder(BorderFactory.createEmptyBorder(24, 40, 24, 40));

        JLabel heading = new JLabel("Create an account");
        heading.setFont(HEADING_FONT);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        rolePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rolePanel.setMaximumSize(new Dimension(ROW_SIZE.width, 54));
        rolePanel.add(btnRoleStudent);
        rolePanel.add(btnRoleOrganiser);

        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegister.setMaximumSize(new Dimension(ROW_SIZE.width, 44));
        btnGoLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnGoLogin.setMaximumSize(new Dimension(ROW_SIZE.width, 28));

        // single column, but each row is "label : input" on one line
        formSide.add(heading);
        formSide.add(Box.createVerticalStrut(16));
        formSide.add(rolePanel);
        formSide.add(Box.createVerticalStrut(16));
        formSide.add(labeledRow("First name", txtFirstName));
        formSide.add(Box.createVerticalStrut(8));
        formSide.add(labeledRow("Last name", txtLastName));
        formSide.add(Box.createVerticalStrut(8));
        formSide.add(labeledRow("Email", txtEmail));
        formSide.add(Box.createVerticalStrut(8));
        formSide.add(conditionalWrapper);
        formSide.add(Box.createVerticalStrut(8));
        formSide.add(labeledRow("Password", pwdPassword));
        formSide.add(Box.createVerticalStrut(8));
        formSide.add(labeledRow("Confirm password", pwdConfirm));
        formSide.add(Box.createVerticalStrut(16));
        formSide.add(btnRegister);
        formSide.add(Box.createVerticalStrut(8));
        formSide.add(btnGoLogin);

        // assemble
        JPanel root = new JPanel(new BorderLayout());
        root.add(purplePanel, BorderLayout.WEST);
        root.add(formSide, BorderLayout.CENTER);
        setContentPane(root);

        // link to login screen
        btnGoLogin.addActionListener(e -> {
            new Login().setVisible(true);
            this.dispose();
        });

        btnRegister.addActionListener(e -> {
            RegisterRequestDTO request = new RegisterRequestDTO();

            request.setRole("STUDENT");
            request.setEmail(txtEmail.getText().trim());
            request.setPassword(new String("mypassword"));
            request.setFacultyId(44L);
            request.setStudentNumber(txtStudentNumber.getText().trim());

            register(request);
        });
    }

    private JPanel buildStudentFieldsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(labeledRow("Student number", txtStudentNumber));
        panel.add(Box.createVerticalStrut(8));
        panel.add(labeledRow("Faculty", cmbFacultyStudent));

        return panel;
    }
    private JPanel buildOrganiserFieldsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblPendingNotice = new JLabel("Pending organiser approval.");
        lblPendingNotice.setFont(LABEL_FONT);
        lblPendingNotice.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(labeledRow("Faculty", cmbFacultyOrganiser));
        panel.add(Box.createVerticalStrut(4));
        panel.add(lblPendingNotice);

        return panel;
    }
    // label : input, side by side on one line
    private JPanel labeledRow(String labelText, JComponent field) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setAlignmentX(Component.CENTER_ALIGNMENT);
        row.setMaximumSize(ROW_SIZE);
        row.setPreferredSize(ROW_SIZE);

        JLabel label = new JLabel(labelText + " :");
        label.setFont(LABEL_FONT);
        label.setPreferredSize(new Dimension(LABEL_WIDTH, FIELD_HEIGHT));
        label.setMaximumSize(new Dimension(LABEL_WIDTH, FIELD_HEIGHT));
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        row.add(label);
        row.add(Box.createHorizontalStrut(10));
        row.add(field);
        return row;
    }

    public void register(RegisterRequestDTO request) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            HttpClient client = HttpClient.newHttpClient();

            // ---------------- REGISTER ----------------

            HttpRequest registerRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/auth/register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            mapper.writeValueAsString(request)))
                    .build();

            HttpResponse<String> registerResponse =
                    client.send(registerRequest, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status: " + registerResponse.statusCode());
            System.out.println(registerResponse.body());

            if (registerResponse.statusCode() != 200) {
                JOptionPane.showMessageDialog(
                        null,
                        registerResponse.body(),
                        "Server Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            RegisterResponseDTO registerResult =
                    mapper.readValue(registerResponse.body(), RegisterResponseDTO.class);

            if (!registerResult.isSuccess()) {
                JOptionPane.showMessageDialog(
                        null,
                        registerResult.getMessage(),
                        "Registration Failed",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            String uuid = registerResult.getUuid();

            // ---------------- VERIFY ----------------

            while (true) {

                String pin = JOptionPane.showInputDialog(
                        null,
                        "Enter the verification PIN sent to your email:",
                        "Verify Account",
                        JOptionPane.PLAIN_MESSAGE
                );

                if (pin == null) {
                    return;
                }

                VerifyRequestDTO verifyRequest = new VerifyRequestDTO();
                verifyRequest.setUuid(uuid);
                verifyRequest.setPin(pin);

                HttpRequest httpVerifyRequest = HttpRequest.newBuilder()
                        .uri(new URI("http://localhost:8080/api/auth/verify"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(
                                mapper.writeValueAsString(verifyRequest)))
                        .build();

                HttpResponse<String> verifyResponse =
                        client.send(httpVerifyRequest, HttpResponse.BodyHandlers.ofString());

                if (verifyResponse.statusCode() != 200) {
                    JOptionPane.showMessageDialog(
                            null,
                            verifyResponse.body(),
                            "Server Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                VerifyResponseDTO verifyResult =
                        mapper.readValue(verifyResponse.body(), VerifyResponseDTO.class);

                if (verifyResult.isSuccess()) {
                    JOptionPane.showMessageDialog(
                            null,
                            verifyResult.getMessage(),
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return;
                }

                String message = verifyResult.getMessage();

                // Wrong PIN -> Retry
                if ("Incorrect PIN.".equalsIgnoreCase(message)) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Incorrect PIN. Please try again.",
                            "Verification Failed",
                            JOptionPane.ERROR_MESSAGE
                    );

                    continue;
                }

                // PIN expired -> Resend
                if ("Verification PIN has expired.".equalsIgnoreCase(message)) {

                    int option = JOptionPane.showConfirmDialog(
                            null,
                            "Your PIN has expired.\nWould you like a new one?",
                            "PIN Expired",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (option != JOptionPane.YES_OPTION) {
                        return;
                    }

                    ResendRequestDTO resendRequest = new ResendRequestDTO();
                    resendRequest.setUuid(uuid);

                    HttpRequest resendHttpRequest = HttpRequest.newBuilder()
                            .uri(new URI("http://localhost:8080/api/auth/resend"))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(
                                    mapper.writeValueAsString(resendRequest)))
                            .build();

                    HttpResponse<String> resendResponse =
                            client.send(resendHttpRequest, HttpResponse.BodyHandlers.ofString());

                    if (resendResponse.statusCode() != 200) {
                        JOptionPane.showMessageDialog(
                                null,
                                resendResponse.body(),
                                "Server Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }

                    RegisterResponseDTO resendResult =
                            mapper.readValue(resendResponse.body(), RegisterResponseDTO.class);

                    if (!resendResult.isSuccess()) {
                        JOptionPane.showMessageDialog(
                                null,
                                resendResult.getMessage(),
                                "Resend Failed",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }

                    JOptionPane.showMessageDialog(
                            null,
                            "A new PIN has been generated.\nPlease check your email.",
                            "PIN Sent",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    continue;
                }

                JOptionPane.showMessageDialog(
                        null,
                        message,
                        "Verification Failed",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Unable to communicate with the server.\n\n" + ex.getMessage(),
                    "Connection Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            Register register = new Register();
            register.setVisible(true);
        });
    }
}