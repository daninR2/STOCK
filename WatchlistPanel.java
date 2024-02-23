import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WatchlistPanel extends JPanel {

    public WatchlistPanel() {
        setLayout(new GridLayout(0, 1, 10, 10));
        setBackground(Color.BLACK);

        // Example stock list
        String[] stockTickers = {"AAPL", "GOOGL", "MSFT", "AMZN", "TSLA"};

        // Create buttons for each stock
        for (String stockTicker : stockTickers) {
            JButton stockButton = new JButton(stockTicker);
            stockButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showStockDetails(stockTicker);
                }
            });
            add(stockButton);
        }
    }

    private void showStockDetails(String stockTicker) {
        JFrame stockDetailsFrame = new JFrame("Stock Details - " + stockTicker);
        stockDetailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        stockDetailsFrame.setSize(300, 200);
        stockDetailsFrame.setLocationRelativeTo(null);

        // Components for stock details
        JLabel nameLabel = new JLabel("Stock: " + stockTicker);
        JLabel priceLabel = new JLabel("Price: $" + generateRandomPrice());
        JButton buyButton = new JButton("Buy");
        JButton sellButton = new JButton("Sell");

        // Layout for the stock details frame
        JPanel stockDetailsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        stockDetailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        stockDetailsPanel.add(nameLabel);
        stockDetailsPanel.add(priceLabel);
        stockDetailsPanel.add(buyButton);
        stockDetailsPanel.add(sellButton);

        // Add components to the frame
        stockDetailsFrame.add(stockDetailsPanel);
        stockDetailsFrame.setVisible(true);
    }

    private double generateRandomPrice() {
        return Math.round((Math.random() * 1000) * 100.0) / 100.0;
    }
}
