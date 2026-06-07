import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio {
    private Map<String, Integer> holdings = new HashMap<>();
    private List<String>         history  = new ArrayList<>();

    public void buy(Stock stock, int qty) {
        holdings.put(stock.getSymbol(),
                holdings.getOrDefault(stock.getSymbol(), 0) + qty);
        history.add("Bought " + qty + " " + stock.getSymbol());
    }

    public void sell(Stock stock, int qty) {
        int owned = holdings.getOrDefault(stock.getSymbol(), 0);
        if (owned >= qty) {
            holdings.put(stock.getSymbol(), owned - qty);
            history.add("Sold " + qty + " " + stock.getSymbol());
        } else {
            System.out.println("Not enough shares!");
        }
    }

    public double value(Map<String, Stock> market) {
        double total = 0;
        for (Map.Entry<String, Integer> e : holdings.entrySet()) {
            Stock s = market.get(e.getKey());
            if (s != null) total += e.getValue() * s.getPrice();
        }
        return total;
    }

    // Manual JSON build — no external library needed
    public void save() {
        StringBuilder sb = new StringBuilder("{\n");
        int i = 0;
        for (Map.Entry<String, Integer> e : holdings.entrySet()) {
            sb.append("    \"").append(e.getKey()).append("\": ").append(e.getValue());
            if (++i < holdings.size()) sb.append(",");
            sb.append("\n");
        }
        sb.append("}");
        try (FileWriter fw = new FileWriter("portfolio.json")) {
            fw.write(sb.toString());
            System.out.println("Portfolio saved to portfolio.json");
        } catch (IOException e) {
            System.out.println("Error saving portfolio: " + e.getMessage());
        }
    }

    public Map<String, Integer> getHoldings() { return holdings; }
    public List<String>         getHistory()  { return history; }
}
