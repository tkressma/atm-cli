import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;

public class AccountDatabase {
    static ArrayList<Account> accounts = new ArrayList<>();
    File database = new File("User_Database.csv");

    // Generates an ArrayList of all the accounts in the "database"
    public AccountDatabase() {
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(database));

            while ((line = reader.readLine()) != null) {
                if (!line.contains("AcctName")) {

                    String[] accountDetails = line.split(",");

                    String acctName = accountDetails[0];
                    int acctId = Integer.parseInt(accountDetails[1]);
                    int acctPin = Integer.parseInt(accountDetails[2]);
                    BigDecimal acctBalance = new BigDecimal(accountDetails[3]);

                    accounts.add(new Account(acctName, acctId, acctPin, acctBalance));
                }
            }

            reader.close();
        } catch (Exception e) {
            System.out.println("File not found!");
        }
    }

    /* Searches for the line with the current users ID, then proceeds to
    *  overwrite the line with the newly updated user data. */
    public void updateDatabase(String name, int id, int pin, BigDecimal balance) {
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(database));
            StringBuffer inputBuffer = new StringBuffer();

            String updatedLine = name +","+ id +"," + pin + "," + balance;

            while ((line = reader.readLine()) != null) {
                if (line.contains(String.format("%d", id))) {
                    inputBuffer.append(updatedLine);
                    inputBuffer.append('\n');
                } else {
                    inputBuffer.append(line);
                    inputBuffer.append('\n');
                }
            }
            reader.close();

            FileOutputStream updatedDatabase = new FileOutputStream("User_Database.csv");
            updatedDatabase.write(inputBuffer.toString().getBytes());
            updatedDatabase.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public boolean authenticateUser(int id, int pin) {
        return accounts.stream().anyMatch(account -> account.id == id && account.pin == pin);
    }

    public Account getAccount(int id) {
        return accounts.stream().filter(account -> account.id == id).findFirst().orElse(null);
    }
}
