import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {

    public MainScreen(String username) {
        super("Main Application Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        // Create and configure components
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(welcomeLabel, BorderLayout.CENTER);

        // Add components to the frame
        add(panel);

        // Configure frame settings
        pack(); // Adjust the size of the frame based on its components
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainScreen("TestUser");
            }
        });
    }
}
