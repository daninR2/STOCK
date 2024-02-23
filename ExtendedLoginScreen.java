import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ExtendedLoginScreen extends LoginScreen {

    private UserData userData;  // Added field to hold user data
    private JFrame mainAppScreen;

    public ExtendedLoginScreen() {
        super();
        this.userData = new UserData();  // Initialize user data
    }

    @Override
    protected void onLoginButtonClick() {
        String username = getUsernameField().getText();
        char[] password = getPasswordField().getPassword();
    
        UserData userData = UserData.readUserDataFromFile(username);
    
        if (userData != null && userData.getPassword().equals(new String(password))) {
            dispose();
            SwingUtilities.invokeLater(() -> createMainAppScreen(userData));
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    

    private void createMainAppScreen(UserData userData) {
        mainAppScreen = new JFrame("Main Application Screen");
        mainAppScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainAppScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainAppScreen.setLocationRelativeTo(null);
    
        JPanel graphsPanel = createGraphsPanel();
        JPanel graphsLabelPanel = new JPanel();
        graphsLabelPanel.setBackground(Color.WHITE);
        JLabel graphsLabel = new JLabel("ACCOUNT");
        graphsLabel.setFont(new Font("Times", Font.BOLD, 20));
        graphsLabelPanel.add(graphsLabel);
    
        graphsPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
    
        JPanel watchlistPanel = createWatchlistPanel();
    
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createAccountInfoPanel(userData), watchlistPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(mainAppScreen.getWidth() / 5);
    
        mainAppScreen.add(graphsLabelPanel, BorderLayout.NORTH);
        mainAppScreen.add(splitPane, BorderLayout.CENTER);
    
        mainAppScreen.setVisible(true);
    }

    private JPanel createGraphsPanel() {
        JPanel graphsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawLinearFunction(g);
            }
        };
        graphsPanel.setBackground(Color.WHITE); // Set your own background color

        return graphsPanel;
    }

    private void drawLinearFunction(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2));

        int startX = 50;
        int endX = width - 50;
        int startY = height - 50 - linearFunction(startX);
        int endY = height - 50 - linearFunction(endX);

        g2d.drawLine(startX, startY, endX, endY);
    }

    private int linearFunction(int x) {
        // Example of a simple linear function
        return -2 * x + 200;
    }

    private JPanel createWatchlistPanel() {
        JPanel watchlistPanel = new JPanel(new GridLayout(3, 3));
        watchlistPanel.setBackground(Color.BLACK); // Set your own background color
        String[] stockTickers = {"AAPL", "GOOGL", "MSFT", "AMZN", "TSLA", "AMD", "BMO", "NVDA", "PYPL"};
        double[] prices = {112.23, 98.53, 77.43, 144.23, 201.32, 23.54, 89.10, 165.78, 19.23};

        for (int i = 0; i < stockTickers.length; i++) {
            String stockTicker = stockTickers[i];
            double stockPrice = prices[i];

            JButton stockButton = new JButton(stockTicker + " - $" + stockPrice);
            stockButton.setBackground(Color.BLACK);
            stockButton.setForeground(Color.WHITE);
            stockButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showStockDetails(stockTicker, stockPrice);
                }

                private void showStockDetails(String stockTicker, double stockPrice) {
                    JDialog stockDetailsDialog = createStockDetailsDialog(stockTicker, stockPrice);
                    stockDetailsDialog.setVisible(true);
                }
            });
            watchlistPanel.add(stockButton);
        }

        return watchlistPanel;
    }

    private JPanel createAccountInfoPanel(UserData userData) {
        JPanel accountInfoPanel = new JPanel(new BorderLayout());
        accountInfoPanel.setBackground(Color.BLACK);
    
        JLabel userLabel = new JLabel("User: " + userData.getUsername());
        JLabel balanceLabel = new JLabel("Current Balance: $" + userData.getBalance());
        JLabel holdingsLabel = new JLabel("Current Stock Holdings: " + userData.getStockHoldings());
    
        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        Color labelColor = Color.WHITE;
        userLabel.setFont(labelFont);
        balanceLabel.setFont(labelFont);
        holdingsLabel.setFont(labelFont);
        userLabel.setForeground(labelColor);
        balanceLabel.setForeground(labelColor);
        holdingsLabel.setForeground(labelColor);
    
        JPanel infoLabelsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoLabelsPanel.setBackground(Color.BLACK);
        infoLabelsPanel.add(userLabel);
        infoLabelsPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        infoLabelsPanel.add(balanceLabel);
        infoLabelsPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        infoLabelsPanel.add(holdingsLabel);
    
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Account Information");
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 16));
        titledBorder.setTitleColor(Color.WHITE);
        infoLabelsPanel.setBorder(titledBorder);
    
        accountInfoPanel.add(infoLabelsPanel, BorderLayout.NORTH);
    
        return accountInfoPanel;
    }

    private JDialog createStockDetailsDialog(String stockTicker, double stockPrice) {
        // Create a new dialog
        JDialog stockDetailsDialog = new JDialog((JFrame) null, "Stock Details", true);
        stockDetailsDialog.setSize(300, 150);
        stockDetailsDialog.setLayout(new BorderLayout());
        stockDetailsDialog.setLocationRelativeTo(null);

        // Create a panel to display stock details
        JPanel detailsPanel = new JPanel(new GridLayout(3, 1));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel tickerLabel = new JLabel("Ticker: " + stockTicker);
        JLabel priceLabel = new JLabel("Price: $" + stockPrice);

        detailsPanel.add(tickerLabel);
        detailsPanel.add(priceLabel);

        // Create buttons for Buy and Sell
        JButton buyButton = new JButton("Buy");
        JButton sellButton = new JButton("Sell");

        // Add action listeners to Buy and Sell buttons
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement Buy functionality
                buyStock(stockTicker, stockPrice);
                updateAccountInfoPanel(); // Refresh the account info panel
                stockDetailsDialog.dispose(); // Close the dialog after buying
            }
        });

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement Sell functionality
                sellStock(stockTicker, stockPrice);
                updateAccountInfoPanel(); // Refresh the account info panel
                stockDetailsDialog.dispose(); // Close the dialog after selling
            }
        });

        // Create a panel for Buy and Sell buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        buttonPanel.add(buyButton);
        buttonPanel.add(sellButton);

        // Add panels to the dialog
        stockDetailsDialog.add(detailsPanel, BorderLayout.CENTER);
        stockDetailsDialog.add(buttonPanel, BorderLayout.SOUTH);

        return stockDetailsDialog;
    }

    private void showStockDetails(String stockTicker) {
        // Here, you can open a smaller tab to allow the user to buy/sell the selected stock
        JOptionPane.showMessageDialog(null, "Selected stock: " + stockTicker, "Stock Details", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
    // For testing purposes, add a default user to the file
    // Replace this with actual user registration logic in your application
    // The password should be stored securely (e.g., using hashing)
    String defaultUser = "testuser testpassword 100000.0 AAPL GOOGL MSFT";
    try {
        FileWriter fileWriter = new FileWriter("user_data.txt");
        fileWriter.write(defaultUser);
        fileWriter.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

    SwingUtilities.invokeLater(() -> new ExtendedLoginScreen());
    }   


    private void buyStock(String stockTicker, double stockPrice) {
        // Check if the user has enough balance to buy the stock
        if (userData.getBalance() >= stockPrice) {
            // Subtract the stock price from the user's balance
            double newBalance = userData.getBalance() - stockPrice;
            userData.setBalance(newBalance);
    
            // Add the stock to the user's holdings
            userData.addStockHolding(stockTicker);
    
            // Display a confirmation message
            JOptionPane.showMessageDialog(null, "You have successfully bought " + stockTicker, "Buy Successful", JOptionPane.INFORMATION_MESSAGE);
    
            // Update the account info panel
            updateAccountInfoPanel();
        } else {
            // Display an error message if the user doesn't have enough balance
            JOptionPane.showMessageDialog(null, "Insufficient balance to buy " + stockTicker, "Buy Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void sellStock(String stockTicker, double stockPrice) {
        // Check if the user owns the stock before selling
        if (userData.getStockHoldings().contains(stockTicker)) {
            // Add the stock price to the user's balance
            double newBalance = userData.getBalance() + stockPrice;
            userData.setBalance(newBalance);
    
            // Remove the stock from the user's holdings
            userData.removeStockHolding(stockTicker);
    
            // Display a confirmation message
            JOptionPane.showMessageDialog(null, "You have successfully sold " + stockTicker, "Sell Successful", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Display an error message if the user doesn't own the stock
            JOptionPane.showMessageDialog(null, "You do not own " + stockTicker + " to sell", "Sell Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Add this method to refresh the information displayed in the account info panel
    private void updateAccountInfoPanel() {
        // Get components from the accountInfoPanel
        JLabel holdingsLabel = (JLabel) ((JPanel) ((JPanel) mainAppScreen.getContentPane().getComponent(1)).getComponent(0)).getComponent(0);
        JLabel balanceLabel = (JLabel) ((JPanel) ((JPanel) mainAppScreen.getContentPane().getComponent(1)).getComponent(0)).getComponent(1);
        JLabel gainLossLabel = (JLabel) ((JPanel) ((JPanel) mainAppScreen.getContentPane().getComponent(1)).getComponent(0)).getComponent(2);
    
        // Update labels with new data
        holdingsLabel.setText("Current Stock Holdings: " + userData.getStockHoldings());
        balanceLabel.setText("Current Balance: $" + userData.getBalance());
        gainLossLabel.setText("Net Gain/Loss: " + userData.getNetGainLoss());
    }

class UserData {
    private String username;
    private String password;
    private double balance;
    private ArrayList<String> stockHoldings;

    public UserData(String username, String password, double balance, ArrayList<String> stockHoldings) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.stockHoldings = stockHoldings;
    }

    public UserData() {
        //TODO Auto-generated constructor stub
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<String> getStockHoldings() {
        return stockHoldings;
    }

    public static UserData readUserDataFromFile(String username) {
        try {
            File file = new File("user_data.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");

                if (parts.length >= 2 && parts[0].equals(username)) {
                    String password = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    ArrayList<String> stocks = new ArrayList<>();

                    for (int i = 3; i < parts.length; i++) {
                        stocks.add(parts[i]);
                    }

                    return new UserData(username, password, balance, stocks);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
}

