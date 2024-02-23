import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginScreen() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onLoginButtonClick();
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    protected JTextField getUsernameField() {
        return usernameField;
    }

    protected JPasswordField getPasswordField() {
        return passwordField;
    }

    protected void onLoginButtonClick() {
        String username = getUsernameField().getText();
        char[] password = getPasswordField().getPassword();

        if (username.equals("d") && new String(password).equals("d")) {
            // For simplicity, just print a message
            System.out.println("Login successful for user: " + username);
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginScreen();
            }
        });
    }
}
