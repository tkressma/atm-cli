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
}
