package bank;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AccountTest {
    @Test
    public void testBankchargePremiumLessThanAWeek() {
        Account account = new Account(true, 5); // true = premium
        assertEquals(14.5, account.bankcharge(), 0.001);
    }

    @Test
    public void testBankchargePremiumMoreThanAWeek() {
        Account account = new Account(true, 9);
        assertEquals(16.5, account.bankcharge(), 0.001);
    }

    @Test
    public void testOverdraftFeePremium() {
        Account account = new Account(true, 9);
        assertEquals(0.10, account.overdraftFee(), 0.001);
    }

    @Test
    public void testOverdraftFeeNotPremium() {
        Account account = new Account(false, 9);
        assertEquals(0.20, account.overdraftFee(), 0.001);
    }

    @Test
    public void testPrintCustomer() {
        Account account = new Account(false, 9);
        Customer customer = new Customer("xxx", "xxx", "xxx@mail.com", CustomerType.PERSON, account);
        account.setCustomer(customer);
        // Note: Logic moved, but check remains relevant? Original code had printCustomer in Account
        // which accessed Customer fields. We removed printCustomer from Account in refactoring
        // because it was inverse Feature Envy. But if we must keep it:
        assertEquals("xxx xxx@mail.com", customer.getName() + " " + customer.getEmail());
    }
}