package bankaccount.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {

    @Test
    public void shouldHaveMoreMoneyOnAccountWhenMakingDepositit() {
        LocalDateTime dateTime = LocalDateTime.of(2017, 1, 1, 12,0,0);
        Account account = new Account(UUID.randomUUID().toString());
        account.deposit(BigDecimal.TEN, dateTime);
        assertThat(account.balance()).isEqualByComparingTo("10");
    }

    @Test
    public void shouldHaveLessMoneyOnAccountWhenMakingWithDrawal() {
        LocalDateTime dateTime = LocalDateTime.of(2017, 1, 1, 12,0,0);
        Account account = new Account(UUID.randomUUID().toString());
        account.withdrawal(BigDecimal.TEN, dateTime);
        assertThat(account.balance()).isEqualByComparingTo("-10");
    }
}
