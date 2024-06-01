package com.micro.flow.domain;

import com.micro.flow.exception.BalanceException;
import com.micro.flow.exception.ReplenishException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Setter
@Getter
@Table(name = "account")
@ToString(of = "id")
@NoArgsConstructor
public class Account {
    private static final BigDecimal MIN_BALANCE_VALUE = BigDecimal.ZERO;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Your balance can't be lower than zero!")
    @Min(value = 0, message = "Your balance can't be lower than zero!")
    private BigDecimal balance;

    @NaturalId
    @Column(nullable = false)
    @NotNull(message = "Username must be lowercase and can contain only letters," +
            " numbers, underscores, and dots")
    @Pattern(regexp = "^[a-z0-9_.]+$", message = "Username must be lowercase" +
            " and can contain only letters, numbers, underscores, and dots")
    private String username;

    public Account(String username) {
        this.username = username;
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
        if (isAmountSmallerThanZero(replenishFor)) {
            throw new ReplenishException("The replenish amount must be greater than zero!");
        }
    }

    public void debit(BigDecimal totalPrice) {
        final BigDecimal debitValue = this.balance.subtract(totalPrice);
        tryToDebit(debitValue);
        this.balance = debitValue;
    }

    private void tryToDebit(BigDecimal debitValue) {
        if (isAmountSmallerThanZero(debitValue)) {
            throw new BalanceException("Your balance hasn't enough money! " +
                    "Please replenish it to buy clothes.");
        }
    }

    private boolean isAmountSmallerThanZero(BigDecimal balance) {
        return balance == null
                || balance.compareTo(MIN_BALANCE_VALUE) <= MIN_BALANCE_VALUE.intValue();
    }

}
