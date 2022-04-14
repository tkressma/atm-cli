import java.math.BigDecimal;

public class Account {
    String accountName;
    int accountId;
    int accountPin;
    BigDecimal accountBalance;

    public Account(String acctName, int acctId, int acctPin, BigDecimal acctBalance) {
        this.accountName = acctName;
        this.accountId = acctId;
        this.accountPin = acctPin;
        this.accountBalance = acctBalance;
    }

    public String getAccountFirstName() {
        return this.accountName.split(" ")[0];
    }

    public BigDecimal getAccountBalance() {
        return this.accountBalance;
    }

    public void depositFunds(BigDecimal deposit) {
        this.accountBalance = this.accountBalance.add(deposit);
    }

    public void withdrawFunds(BigDecimal withdrawal) {
        this.accountBalance = this.accountBalance.subtract(withdrawal);
    }
}
