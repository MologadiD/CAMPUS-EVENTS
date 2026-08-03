package za.ac.cput;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Verify extends JFrame {

    private JPasswordField pinField;
    private JButton        submitButton;
    private JButton        resendButton;
    private JLabel         messageLabel;

    public Verify() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Verify OTP");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setLocationRelativeTo(null);
        setResizable(false);


        JPanel outerPanel = new JPanel();
        outerPanel.setBackground(Color.WHITE);
        outerPanel.setLayout(new GridBagLayout());


        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                new EmptyBorder(40, 40, 40, 40)
        ));


        JLabel titleLabel = new JLabel("Verify Your Account");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        JLabel subtitleLabel = new JLabel("Enter the OTP sent to your email");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitleLabel.setForeground(Color.DARK_GRAY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        JLabel pinLabel = new JLabel("OTP Code");
        pinLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        pinLabel.setForeground(Color.BLACK);
        pinLabel.setAlignmentX(Component.LEFT_ALIGNMENT);


        pinField = new JPasswordField();
        pinField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        pinField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        pinField.setBackground(Color.WHITE);
        pinField.setForeground(Color.BLACK);
        pinField.setCaretColor(Color.BLACK);
        pinField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        pinField.setHorizontalAlignment(JTextField.CENTER);
        pinField.setEchoChar('\u2022');
        pinField.setAlignmentX(Component.LEFT_ALIGNMENT);


        messageLabel = new JLabel(" ");
        messageLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        messageLabel.setForeground(Color.DARK_GRAY);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        submitButton = new JButton("Verify OTP");
        submitButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);


        resendButton = new JButton("Resend OTP");
        resendButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        resendButton.setBackground(Color.WHITE);
        resendButton.setForeground(Color.BLACK);
        resendButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        resendButton.setFocusPainted(false);
        resendButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        resendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resendButton.setAlignmentX(Component.LEFT_ALIGNMENT);


        submitButton.addActionListener(e -> handleVerify());
        resendButton.addActionListener(e -> handleResend());


        card.add(titleLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(subtitleLabel);
        card.add(Box.createVerticalStrut(30));
        card.add(pinLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(pinField);
        card.add(Box.createVerticalStrut(10));
        card.add(messageLabel);
        card.add(Box.createVerticalStrut(20));
        card.add(submitButton);
        card.add(Box.createVerticalStrut(12));
        card.add(resendButton);

        outerPanel.add(card);
        add(outerPanel);
        setVisible(true);
    }


    private void handleVerify() {
        String otp = new String(pinField.getPassword()).trim();

        if (otp.isEmpty()) {
            showMessage("Please enter your OTP code.");
            return;
        }


        showMessage("Connecting to /auth/verify...");
    }


    private void handleResend() {


        pinField.setText("");
        showMessage("OTP resent! Check your email.");
        resendButton.setEnabled(false);

        Timer timer = new Timer(30000, e -> {
            resendButton.setEnabled(true);
            resendButton.setText("Resend OTP");
        });
        resendButton.setText("Resend OTP (wait 30s)");
        timer.setRepeats(false);
        timer.start();
    }


    private void showMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setForeground(Color.DARK_GRAY);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Verify::new);
    }
}
