import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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

    // Other methods...
}
