package com.micro.flow.domain;

import com.micro.flow.exception.BalanceException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Setter
@Getter
@Table(name = "account")
@ToString(of = "id")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 0, message = "Your balance can't be lower than zero!")
    private BigDecimal balance;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Account() {
        this.balance = BigDecimal.ZERO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return this.id != null && Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void replenish(BigDecimal replenishFor) {
        ifReplenishForSmallerThanZeroThrowExc(replenishFor);
        this.balance = balance.add(replenishFor);
    }

    private void ifReplenishForSmallerThanZeroThrowExc(BigDecimal replenishFor) {
        if (isBalanceSmallerThanZero(replenishFor)) {
            throw new IllegalArgumentException("The replenish amount must be greater than zero!");
        }
    }

    public void debit(BigDecimal totalPrice) {
//     final BigDecimal copyBalance = this.balance;  version for improving!!!
        final BigDecimal debitValue = this.balance.subtract(totalPrice); // todo: check if it right!
        tryToDebit(debitValue);
        this.balance = debitValue;
    }

    private void tryToDebit(BigDecimal debitValue) {
        if (isBalanceSmallerThanZero(debitValue)) {
            throw new BalanceException("Your balance hasn't enough money! " +
                    "Please replenish it to buy clothes.");
        }
    }

    private boolean isBalanceSmallerThanZero(BigDecimal balance) {
        return balance.compareTo(BigDecimal.ZERO) < 0;
    }

}