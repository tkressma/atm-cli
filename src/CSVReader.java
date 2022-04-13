import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;

public class CSVReader {
    static ArrayList<Account> accounts = new ArrayList<>();
    File database = new File("User_Database.csv");

    /* Generates an ArrayList of all the accounts in the "database"
     *  Utilizes BufferedReader to scan each line and dynamically create
     *  an account based off of the data. */
    public CSVReader() {
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

    public boolean authenticateUser(int id, int pin) {
        return accounts.stream().anyMatch(account -> account.id == id && account.pin == pin);
    }
}
