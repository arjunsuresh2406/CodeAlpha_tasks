import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Market Data
        Map<String, Stock> market = new LinkedHashMap<>();
        market.put("AAPL", new Stock("AAPL", 180));
        market.put("TSLA", new Stock("TSLA", 700));
        market.put("GOOG", new Stock("GOOG", 2800));

        User    user = new User("Investor");
        Scanner sc   = new Scanner(System.in);

        while (true) {
            System.out.println("\n1.View Market  2.Buy  3.Sell  4.Portfolio Value  5.Exit");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();

            if (choice.equals("1")) {
                // View Market
                for (Stock s : market.values()) {
                    System.out.println(s.getSymbol() + ": $" + s.getPrice());
                }

            } else if (choice.equals("2")) {
                // Buy
                System.out.print("Stock: ");
                String sym = sc.nextLine().trim().toUpperCase();
                if (!market.containsKey(sym)) {
                    System.out.println("Stock not found!");
                    continue;
                }
                System.out.print("Qty: ");
                try {
                    int qty = Integer.parseInt(sc.nextLine().trim());
                    user.getPortfolio().buy(market.get(sym), qty);
                    System.out.println("Bought " + qty + " share(s) of " + sym);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid quantity.");
                }

            } else if (choice.equals("3")) {
                // Sell
                System.out.print("Stock: ");
                String sym = sc.nextLine().trim().toUpperCase();
                if (!market.containsKey(sym)) {
                    System.out.println("Stock not found!");
                    continue;
                }
                System.out.print("Qty: ");
                try {
                    int qty = Integer.parseInt(sc.nextLine().trim());
                    user.getPortfolio().sell(market.get(sym), qty);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid quantity.");
                }

            } else if (choice.equals("4")) {
                // Portfolio Value
                System.out.println("Holdings: " + user.getPortfolio().getHoldings());
                System.out.printf("Portfolio Value: $%.2f%n",
                        user.getPortfolio().value(market));

            } else if (choice.equals("5")) {
                // Save & Exit
                user.getPortfolio().save();
                System.out.println("Portfolio saved.");
                break;

            } else {
                System.out.println("Invalid choice. Enter 1-5.");
            }
        }

        sc.close();
    }
}
