package bank;

public class Account {
    private String iban;
    private boolean isPremium;
    private int daysOverdrawn;
    private Money money;
    private Customer customer;

    public Account(boolean isPremium, int daysOverdrawn) {
        super();
        this.isPremium = isPremium;
        this.daysOverdrawn = daysOverdrawn;
    }

    public double bankcharge() {
        double result = 4.5;
        result += overdraftCharge();
        return result;
    }

    private double overdraftCharge() {
        if (isPremium) {
            double result = 10;
            if (getDaysOverdrawn() > 7)
                result += (getDaysOverdrawn() - 7) * 1.0;
            return result;
        } else
            return getDaysOverdrawn() * 1.75;
    }

    public double overdraftFee() {
        if (isPremium) {
            return 0.10;
        } else {
            return 0.20;
        }
    }

    public void withdraw(double sum, String currency, double companyOverdraftDiscount) {
        if (!getCurrency().equals(currency)) {
            throw new RuntimeException("Can't extract withdraw " + currency);
        }

        double currentMoney = getMoneyAmount();
        double overdraftFee = overdraftFee();

        if (isPremium) {
            overdraftFee *= companyOverdraftDiscount;
            if(companyOverdraftDiscount < 1.0) {
                overdraftFee /= 2.0;
            }
        } else {
            overdraftFee *= companyOverdraftDiscount;
        }

        if (currentMoney < 0) {
            double totalCost = sum + (sum * overdraftFee);
            // ВИПРАВЛЕНО ТУТ: було setMoneyAmount, стало setMoney
            setMoney(currentMoney - totalCost);
        } else {
            // І ТУТ
            setMoney(currentMoney - sum);
        }
    }

    public int getDaysOverdrawn() {
        return daysOverdrawn;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    // Цей метод ми викликаємо зверху
    public void setMoney(double amount) {
        this.money = new Money(amount, (this.money != null) ? this.money.getCurrency() : "EUR");
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    public double getMoneyAmount() {
        return money != null ? money.getAmount() : 0.0;
    }

    public String getCurrency() {
        return money != null ? money.getCurrency() : "";
    }

    public void setCurrency(String currency) {
        this.money = new Money(getMoneyAmount(), currency);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public String getTypeString() {
        return isPremium ? "premium" : "normal";
    }

    public String printAccountDetails() {
        return "Account: IBAN: " + getIban() + ", Money: " + getMoneyAmount();
    }
}