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
    }


    public static void main(String[] args) {
       Login login = new Login();
       login.setVisible(true);
    }
}
