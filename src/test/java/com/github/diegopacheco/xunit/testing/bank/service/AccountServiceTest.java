package com.github.diegopacheco.xunit.testing.bank.service;

import java.math.BigDecimal;

import com.github.diegopacheco.xunit.testing.bank.model.Account;
import com.github.diegopacheco.xunit.testing.bank.model.User;
import com.github.diegopacheco.xunit.testing.bank.model.Type;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountServiceTest {

    @Test
    public void testDeposit(){
        Account a  = new Account();
        AccountService ass = new AccountService();
        ass.deposit(a, new BigDecimal(100));
        Assertions.assertEquals(new BigDecimal(100), a.getValue());
    }

    @Test
    public void testDepositCents(){
        Account a  = new Account();
        AccountService ass = new AccountService();
        ass.deposit(a, new BigDecimal(100.25));
        Assertions.assertEquals(new BigDecimal(100.25), a.getValue());
    }

    @Test
    public void testDepositNull(){
        Account a  = new Account();
        AccountService ass = new AccountService();
        ass.deposit(a, null);
        Assertions.assertEquals(new BigDecimal(0), a.getValue());
    }

    @Test
    public void testDepositBigNumber(){
        Account a  = new Account();
        AccountService ass = new AccountService();
        ass.deposit(a, new BigDecimal("5000000000000"));
        Assertions.assertEquals(new BigDecimal("5000000000000"), a.getValue());
    }

    @Test
    public void testDepositNegative(){
        Account a  = new Account();
        AccountService ass = new AccountService();
        ass.deposit(a, new BigDecimal(-100));
        Assertions.assertEquals(new BigDecimal(0), a.getValue());
    }

    @Test
    public void testWithdraw(){
        Account a  = new Account();
        a.setValue(new BigDecimal(100));
        AccountService ass = new AccountService();
        ass.withdraw(a, new BigDecimal(10));
        Assertions.assertEquals(new BigDecimal(90), a.getValue());
    }

    @Test
    public void testWithdrawCents(){
        Account a  = new Account();
        a.setValue(new BigDecimal(100));
        AccountService ass = new AccountService();
        ass.withdraw(a, new BigDecimal(0.25));
        Assertions.assertEquals(new BigDecimal(99.75), a.getValue());
    }

    @Test
    public void testWithdrawNull(){
        Account a  = new Account();
        a.setValue(new BigDecimal(100));
        AccountService ass = new AccountService();
        ass.withdraw(a, null);
        Assertions.assertEquals(new BigDecimal(100), a.getValue());
    }

    @Test
    public void testWithdrawBigNumber(){
        Account a  = new Account();
        a.setValue(new BigDecimal("5000000000000"));
        AccountService ass = new AccountService();
        ass.withdraw(a, new BigDecimal("4000000000000"));
        Assertions.assertEquals(new BigDecimal("1000000000000"), a.getValue());
    }

    @Test
    public void testWithdrawNegative(){
        Account a  = new Account();
        a.setValue(new BigDecimal(100));
        AccountService ass = new AccountService();
        ass.withdraw(a, new BigDecimal(-100));
        Assertions.assertEquals(new BigDecimal(100), a.getValue());
    }

    @Test
    public void testWithdrawInsufficientFund(){
        Account a  = new Account();
        a.setValue(new BigDecimal(100));
        AccountService ass = new AccountService();
        boolean wr = ass.withdraw(a, new BigDecimal(110));
        Assertions.assertEquals(wr, false);
        Assertions.assertEquals(new BigDecimal(100), a.getValue());
    }

    @Test
    public void testWithdrawInsufficientFundWithTax(){
        Account a  = new Account();
        a.setType(Type.SAVING);
        a.setValue(new BigDecimal(100));
        AccountService ass = new AccountService();
        boolean wr = ass.withdraw(a, new BigDecimal(100));
        Assertions.assertEquals(wr, false);
        Assertions.assertEquals(new BigDecimal(100), a.getValue());
    }
    
    @Test
    public void testSavingEarning(){
        Account a  = new Account();
        a.setValue(new BigDecimal(100));
        a.setType(Type.SAVING);
        AccountService ass = new AccountService();
        ass.simulateMinute(a);
        BigDecimal expected = new BigDecimal(100).add(new BigDecimal(100).multiply(new BigDecimal(0.22)));
        Assertions.assertEquals(expected, a.getValue());
    }

    @Test
    public void testCheck(){
        Account a  = new Account();
        a.setValue(new BigDecimal(100));
        AccountService ass = new AccountService();
        String balance = ass.check(a);
        Assertions.assertEquals("Account balance: $100", balance);
    }

    @Test
    public void testCheckZero(){
        Account a  = new Account();
        a.setValue(new BigDecimal(0));
        AccountService ass = new AccountService();
        String balance = ass.check(a);
        Assertions.assertEquals("Account balance: $0", balance);
    }

    @Test
    public void testCheckNegative(){
        Account a  = new Account();
        a.setValue(new BigDecimal(-100));
        AccountService ass = new AccountService();
        String balance = ass.check(a);
        Assertions.assertEquals("Account balance: -$100", balance);
    }

    @Test
    public void testTransfer(){
        Account a1  = new Account();
        User u1 = new User();
        u1.setCpf("123123123123");
        a1.setValue(new BigDecimal(100));
        a1.setUser(u1);

        Account a2  = new Account();
        User u2 = new User();
        u2.setCpf("32132132132");
        a2.setValue(new BigDecimal(100));
        a2.setUser(u2);
        
        BigDecimal transfered = new BigDecimal(50);
        AccountService ass = new AccountService();
        ass.transfer(a1, a2, transfered);

        BigDecimal taxedValue = new BigDecimal(50).add(new BigDecimal(50).multiply(new BigDecimal(0.05)));
        BigDecimal expectedA1Value = new BigDecimal(100).subtract(taxedValue);
        Assertions.assertEquals(expectedA1Value, a1.getValue());
        Assertions.assertEquals(new BigDecimal(150), a2.getValue());
    }
}