package com.github.diegopacheco.xunit.testing.bank.model;

import java.math.BigDecimal;

public class Account{

    private int id;
    private User user;
    private BigDecimal value;
    private Type type;

    public int getId() {
		return this.id;
	}

    public void setId(int id) {
		this.id = id;
	}

    public User getUser() {
		return this.user;
	}

    public void setUser(User user) {
		this.user = user;
	}

    public BigDecimal getValue() {
		return this.value;
	}

    public void setValue(BigDecimal value) {
		this.value = value;
	}

    public Type getType() {
		return this.type;
	}

    public void setType(Type type) {
		this.type = type;
	}

    public Account() {
        this.value = new BigDecimal(0);
    }
}