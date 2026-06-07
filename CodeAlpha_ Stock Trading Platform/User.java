public class User {
    private String    name;
    private Portfolio portfolio;

    public User(String name) {
        this.name      = name;
        this.portfolio = new Portfolio();
    }

    public String    getName()      { return name; }
    public Portfolio getPortfolio() { return portfolio; }
}
