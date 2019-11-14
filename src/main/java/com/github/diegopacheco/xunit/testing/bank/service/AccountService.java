package com.github.diegopacheco.xunit.testing.bank.service;

import java.math.BigDecimal;

import com.github.diegopacheco.xunit.testing.bank.model.Account;
import com.github.diegopacheco.xunit.testing.bank.model.Type;

public class AccountService{

    public boolean deposit(Account account, BigDecimal value) {
        if (value == null) {
            return false;
        }

        if (new BigDecimal(0).compareTo(value) == 1) {
            return false;
        }

        account.setValue(account.getValue().add(value));
        return true;
    }

    public boolean withdraw(Account account, BigDecimal value) {
        if (value == null) {
            return false;
        }

        if (new BigDecimal(0).compareTo(value) == 1) {
            return false;
        }

        BigDecimal tax = new BigDecimal(0);

        if (account.getType() == Type.SAVING) {
            tax = new BigDecimal(0.02);
        }

        BigDecimal taxedValue = value.add(value.multiply(tax));

        if (taxedValue.compareTo(account.getValue()) == 1) {
            return false;
        }

        account.setValue(account.getValue().subtract(taxedValue));
        return true;
    }

    public void simulateMinute(Account account) {
        if (account.getType() == Type.SAVING) {
            BigDecimal earning = account.getValue().multiply(new BigDecimal(0.22));
            BigDecimal value = account.getValue().add(earning);
            account.setValue(value);
        }
    }

    public String check(Account account) {
        if (new BigDecimal(0).compareTo(account.getValue()) == 1) {
            return "Account balance: -$" + account.getValue().abs();
        }
        return "Account balance: $" + account.getValue();
    }

    public boolean transfer(Account account1, Account account2, BigDecimal value) {
        if (
            account1 == null ||
            account2 == null ||
            value == null ||
            account1.getUser() == null ||
            account2.getUser() == null
        ) {
            return false;
        }

        double tax = 0;

        if (!account1.getUser().getCpf().equals(account2.getUser().getCpf())) {
            tax = 0.05;
        }

        if (account1.getType() == Type.SAVING) {
            tax += 0.02;
        }

        BigDecimal taxedValue = value.add(value.multiply(new BigDecimal(tax)));

        if (taxedValue.compareTo(account1.getValue()) == 0) {
            return false;
        }

        account1.setValue(account1.getValue().subtract(taxedValue));
        account2.setValue(account2.getValue().add(value));

        return true;
    }

}