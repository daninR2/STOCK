import javax.swing.*;
import java.awt.*;

public class GraphsPanel extends JPanel {

    public GraphsPanel() {
        setBackground(Color.BLUE);  // Placeholder color
        setPreferredSize(new Dimension(300, 0)); // Initial size

        JLabel graphLabel = new JLabel("Graphs");
        graphLabel.setHorizontalAlignment(JLabel.CENTER);
        graphLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font size
        add(graphLabel, BorderLayout.NORTH);
    }
}
