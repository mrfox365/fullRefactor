package bank;

public class Customer {
    private String name;
    private String surname;
    private String email;
    private CustomerType customerType;
    private Account account;
    private double companyOverdraftDiscount = 1.0;

    public Customer(String name, String surname, String email, CustomerType customerType, Account account) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.customerType = customerType;
        this.account = account;
    }

    public Customer(String name, String email, Account account, double companyOverdraftDiscount) {
        this.name = name;
        this.email = email;
        this.customerType = CustomerType.COMPANY;
        this.account = account;
        this.companyOverdraftDiscount = companyOverdraftDiscount;
        // Fix for Company logic: Premium companies got extra discount in original code logic
        // We handle this by passing the raw discount, Account handles the "Premium/Company" calculation
    }

    public void withdraw(double sum, String currency) {
        // Refactored: Delegating to Account (Feature Envy fix)
        // Check if Person (no discount) or Company (discount)
        double discountFactor = (customerType == CustomerType.COMPANY) ? companyOverdraftDiscount : 1.0;
        account.withdraw(sum, currency, discountFactor);
    }

    public String getName() { return name; }
    public String getEmail() { return email; }

    // --- Printing Methods (Refactored) ---

    public String printCustomerDaysOverdrawn() {
        return getFullName() + "Account: IBAN: " + account.getIban() + ", Days Overdrawn: " + account.getDaysOverdrawn();
    }

    public String printCustomerMoney() {
        return getFullName() + account.printAccountDetails();
    }

    public String printCustomerAccount() {
        return account.printAccountDetails() + ", Account type: " + account.getTypeString();
    }

    private String getFullName() {
        return name + " " + ((surname != null) ? surname + " " : "");
    }
}