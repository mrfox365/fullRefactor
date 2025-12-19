package bank;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AccountTest {
    @Test
    public void testBankchargePremiumLessThanAWeek() {
        Account account = getPremiumAccount(5);
        assertEquals(14.5, account.bankcharge(), 0.001);
    }

    @Test
    public void testBankchargePremiumMoreThanAWeek() {
        Account account = getPremiumAccount(9);
        assertEquals(16.5, account.bankcharge(), 0.001);
    }

    @Test
    public void testOverdraftFeePremium() {
        Account account = getPremiumAccount(9);
        assertEquals(0.10, account.overdraftFee(), 0.001);
    }

    @Test
    public void testOverdraftFeeNotPremium() {
        Account account = getNormalAccount();
        assertEquals(0.20, account.overdraftFee(), 0.001);
    }

    @Test
    public void testPrintCustomer() {
        Account account = getNormalAccount();
        Customer customer = new Customer("xxx", "xxx", "xxx@mail.com", CustomerType.PERSON, account);
        account.setCustomer(customer);
        assertEquals("xxx xxx@mail.com", account.printCustomer());
    }

    private Account getNormalAccount() {
        AccountType premium = new AccountType(false);
        return new Account(premium, 9);
    }

    private Account getPremiumAccount(int daysOverdrawn) {
        AccountType normal = new AccountType(true);
        return new Account(normal, daysOverdrawn);
    }
}