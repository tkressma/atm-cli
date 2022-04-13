import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;

public class CSVReader {
    static ArrayList<Account> accounts = new ArrayList<>();
    File database = new File("User_Database");

    public CSVReader() {
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader((database)));

            while ((line = reader.readLine()) != null) {
                String[] accountDetails = line.split(",");

                String acctName = accountDetails[0];
                int acctId = Integer.parseInt(accountDetails[1]);
                int acctPin = Integer.parseInt(accountDetails[2]);
                BigDecimal acctBalance = new BigDecimal(accountDetails[3]);

                accounts.add(new Account(acctName, acctId, acctPin, acctBalance));
            }
        } catch (Exception e) {
            System.out.println("Error loading file.");
        }
    }
}
