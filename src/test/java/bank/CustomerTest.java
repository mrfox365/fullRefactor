package bank;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CustomerTest {
    // Tests remain logic-same, but setup helpers change to remove AccountType

    @Test
    public void testWithdrawPersonWithNormalAccount() {
        Account account = getAccountByTypeAndMoney(false, 34.0);
        Customer customer = getPersonCustomer(account);
        customer.withdraw(10, "EUR");
        assertEquals(24.0, account.getMoneyAmount(), 0.001);
    }

    @Test
    public void testWithdrawPersonWithNormalAccountAndOverdraft() {
        Account account = getAccountByTypeAndMoney(false, -10.0);
        Customer customer = getPersonCustomer(account);
        customer.withdraw(10, "EUR");
        assertEquals(-22.0, account.getMoneyAmount(), 0.001);
    }

    @Test
    public void testWithdrawPersonWithPremiumAccount() {
        Account account = getAccountByTypeAndMoney(true, 34.0);
        Customer customer = getPersonCustomer(account);
        customer.withdraw(10, "EUR");
        assertEquals(24.0, account.getMoneyAmount(), 0.001);
    }

    @Test
    public void testWithdrawPersonWithPremiumAccountAndOverdraft() {
        Account account = getAccountByTypeAndMoney(true, -10.0);
        Customer customer = getPersonCustomer(account);
        customer.withdraw(10, "EUR");
        assertEquals(-21.0, account.getMoneyAmount(), 0.001);
    }

    // ... (Company tests same logic) ...

    @Test
    public void testWithdrawCompanyWithPremiumAccountAndOverdraft() {
        Account account = getAccountByTypeAndMoney(true, -10);
        Customer customer = getCompanyCustomer(account);
        customer.withdraw(10, "EUR");
        assertEquals(-20.25, account.getMoneyAmount(), 0.001);
    }

    @Test
    public void testPrintCustomerDaysOverdrawn() {
        Customer customer = getPersonWithAccount(false);
        assertEquals("danix dan Account: IBAN: RO023INGB434321431241, Days Overdrawn: 9", customer.printCustomerDaysOverdrawn());
    }

    // ... (Other print tests) ...

    private Customer getPersonWithAccount(boolean premium) {
        Account account = new Account(premium, 9);
        Customer customer = getPersonCustomer(account);
        account.setIban("RO023INGB434321431241");
        account.setMoney(34.0);
        account.setCurrency("EUR");
        return customer;
    }

    private Account getAccountByTypeAndMoney(boolean premium, double money) {
        Account account = new Account(premium, 9);
        account.setIban("RO023INGB434321431241");
        account.setMoney(money);
        account.setCurrency("EUR");
        return account;
    }

    private Customer getPersonCustomer(Account account) {
        Customer customer = new Customer("danix", "dan", "dan@mail.com", CustomerType.PERSON, account);
        account.setCustomer(customer);
        return customer;
    }

    private Customer getCompanyCustomer(Account account) {
        Customer customer = new Customer("company", "company@mail.com", account, 0.50);
        account.setCustomer(customer);
        return customer;
    }
}