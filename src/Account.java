import java.math.BigDecimal;

public class Account {
    String name;
    int id;
    int pin;
    BigDecimal balance;

    public Account(String acctName, int acctId, int acctPin, BigDecimal acctBalance) {
        this.name = acctName;
        this.id = acctId;
        this.pin = acctPin;
        this.balance = acctBalance;
    }

    public String getFirstName() {
        return this.name.split(" ")[0];
    }

    public BigDecimal getAccountBalance() {
        return this.balance;
    }

    public void depositFunds(BigDecimal deposit) {
        this.balance = this.balance.add(deposit);
    }

    public void withdrawFunds(BigDecimal withdrawal) {
        this.balance = this.balance.subtract(withdrawal);
    }
}
