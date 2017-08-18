package bankaccount.domain;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Account {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.00");

    private final String accountId;

    private final List<Transaction> transactions;

    public Account(String accountId) {
        this.accountId = accountId;
        transactions = new ArrayList<>();
    }

    public Account(String accountId, List<Transaction> transactions) {
        this.accountId = accountId;
        this.transactions = transactions;
    }

    public List<Transaction> transactions() {
        return Collections.unmodifiableList(transactions);
    }

    public String getAccountId() {
        return accountId;
    }
    
    public void deposit(BigDecimal amount, LocalDateTime dateTime) {
        transactions.add(new Transaction(TransactionType.DEPOSIT, amount, dateTime));
    }

    public void withdrawal(BigDecimal amount, LocalDateTime dateTime) {
        transactions.add(new Transaction(TransactionType.WITHDRAWAL, amount, dateTime));
    }

    public BigDecimal balance() {
        return transactions.stream()
                .map(this::toBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    private BigDecimal toBalance(Transaction transaction) {
        if (transaction.getType() == TransactionType.DEPOSIT) {
            return transaction.getAmount();
        } else {
            return transaction.getAmount().negate();
        }
    }

    public static Optional<Account> fetchFromRepository(AccountRepository repository, String accountId) {
        return repository.findById(accountId);
    }

    public void saveInto(AccountRepository accountRepository) {
        accountRepository.save(this);
    }

    public void print(Printer printer) {
        printer.printLn(String.format("Account balance : %s", balance()));
        printer.printLn("Transaction history: ");
        transactions.forEach(transaction -> printTransaction(printer, transaction));

    }

    private void printTransaction(Printer printer, Transaction transaction) {
        printer.printLn(String.format("$s : $s of %s $",
                transaction.getDate().format(DATE_TIME_FORMATTER),
                transaction.getType().toString(),
                DECIMAL_FORMAT.format(transaction.getAmount())));
    }
}
