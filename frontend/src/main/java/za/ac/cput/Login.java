package za.ac.cput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    private final ButtonGroup roleGroup = new ButtonGroup();
    private JToggleButton btnRoleStudent;
    private JToggleButton btnRoleOrganiser;
    private JToggleButton btnRoleAdmin;

    private JTextField txtIdentifier;
    private JPasswordField pwdPassword;
    private JButton btnLogin;
    private JButton btnGoRegister;

    public Login() {

        // set up the window
        setTitle("Campus Events - Sign in");
        setSize(960,720);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // create the components
        btnRoleStudent = new JToggleButton("Student", true);
        btnRoleOrganiser = new JToggleButton("Organiser");
        btnRoleAdmin = new JToggleButton("Admin");
        roleGroup.add(btnRoleStudent);
        roleGroup.add(btnRoleOrganiser);
        roleGroup.add(btnRoleAdmin);

        Dimension roleBtnSize = new Dimension(140, 40);
        for (JToggleButton b : new JToggleButton[]{btnRoleStudent, btnRoleOrganiser, btnRoleAdmin}) {
            b.setPreferredSize(roleBtnSize);
            b.setFocusPainted(false);
        }

        txtIdentifier = new JTextField(20);
        pwdPassword = new JPasswordField(20);
        btnLogin = new JButton("Sign in");
        btnGoRegister = new JButton("Create an account");

        JPanel root = new JPanel(new BorderLayout(0, 24));
             root.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

             JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
             rolePanel.add(btnRoleStudent);
             rolePanel.add(btnRoleOrganiser);
             rolePanel.add(btnRoleAdmin);
             root.add(rolePanel, BorderLayout.NORTH);

             JPanel formPanel = new JPanel(new GridBagLayout());
             GridBagConstraints gbc = new GridBagConstraints();
             gbc.insets = new Insets(8, 8, 8, 8);
             gbc.fill = GridBagConstraints.HORIZONTAL;
             gbc.gridx = 0;
             gbc.gridwidth = 1;

             gbc.gridy = 0;
             formPanel.add(new JLabel("Student number / Email:"), gbc);
             gbc.gridy = 1;
             formPanel.add(txtIdentifier, gbc);

             gbc.gridy = 2;
             formPanel.add(new JLabel("Password:"), gbc);
             gbc.gridy = 3;
             formPanel.add(pwdPassword, gbc);

             gbc.gridy = 4;
             formPanel.add(btnLogin, gbc);

             gbc.gridy = 5;
             formPanel.add(btnGoRegister, gbc);

             // Center the form in the wide window instead of stretching it edge to edge
             JPanel formWrapper = new JPanel(new GridBagLayout());
             formWrapper.add(formPanel);
             root.add(formWrapper, BorderLayout.CENTER);

             setContentPane(root);
    }


    public static void main(String[] args) {
        try {
                   UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
               } catch (UnsupportedLookAndFeelException e) {
                   e.printStackTrace();
               }

               SwingUtilities.invokeLater(() -> {
                   Login login = new Login();
                   login.setVisible(true);
               });
    }
}
