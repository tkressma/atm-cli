import java.math.BigDecimal;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ATM {
    private static AccountDatabase accountDatabase = new AccountDatabase();
    private static Scanner scanner = new Scanner(System.in);
    private static Account currentUser;

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        System.out.println("Good day! Please enter you account details to get started.");
        login();
        // Once a user logs in, display the welcome message followed by the main menu.
        System.out.println("\nHello, " + currentUser.getFirstName() + "!");
        mainMenu();

    }

    /* Retrieve ID and PIN from the user. Validate the account information with the "database".
     *  If the account is valid, "login" the current user by updating currentUser.
     *  If the details provided are invalid, prompt the user to re-enter the ID and PIN. */
    public static void login() {
        int accountId = getLoginInput(8);
        int accountPin = getLoginInput(4);

        while (!accountDatabase.authenticateUser(accountId, accountPin)) {
            System.out.println("\nInvalid Credentials. Please re-enter your account ID and Pin.\n");
            accountId = getLoginInput(8);
            accountPin = getLoginInput(4);
        }

        currentUser = accountDatabase.getAccount(accountId);
    }

    /* Retrieves account ID/PIN based on the input length provided.
     *  If the input length is 8, then prompt the user for an ID.
     *  If the input length is 4, then prompt the user for a PIN. */
    public static int getLoginInput(int inputLength) {
        String inputType = inputLength == 8 ? "ID" : "PIN";

        System.out.printf("Enter your %s: ", inputType);;
        String userInput = scanner.nextLine();

        while (!isValidInput(userInput, inputLength)) {
            System.out.printf("Invalid %s (Must be %d digits). Try again: ", inputType, inputLength);
            userInput = scanner.nextLine();
        }

        return Integer.parseInt(userInput);
    }

    public static void logout() {
        System.out.println("\nThank you for using our bank. Have a wonderful day!\n");
        currentUser = null;
        start();
    }

    /* Determines whether the user input is valid based on ID/PIN length.
     *  IF an ID is valid, it is strictly 8 digits.
     *  IF a PIN is valid, it is strictly 4 digits. */
    public static boolean isValidInput(String userInput, int inputLength) {
        String REGEX = "\\d{" + inputLength + "}";
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(userInput);

        return matcher.matches();
    }

    public static void mainMenu() {
        System.out.println("\nEnter the number relating to the selection you would like to make:");
        System.out.println("(1) - View Balance");
        System.out.println("(2) - Withdraw Funds");
        System.out.println("(3) - Deposit Funds");
        System.out.println("(4) - Exit");

        String userSelection = scanner.nextLine();

        while (!isValidInput(userSelection, 1)) {
            System.out.println("\nPlease enter a valid selection.");
            userSelection = scanner.nextLine();
        }

        switch(Integer.parseInt(userSelection)) {
            case 1:
                System.out.println("\nAccount Balance\nYour balance is: " + currentUser.getAccountBalance());
                continuePrompt();
                break;
            case 2:
                withdraw();
                continuePrompt();
                break;
            case 3:
                deposit();
                continuePrompt();
                break;
            case 4:
                logout();
            default:
                System.out.println("\nPlease enter a valid selection");
                mainMenu();
        }
    }

    public static void deposit() {
        System.out.println("\nDeposit funds into your account.\n");
        System.out.println("How much would you like to deposit?");
        String userDepositAmount = scanner.nextLine();

        while (!isValidTransactionAmount(userDepositAmount)) {
            System.out.println("Please enter a valid amount. ($1 to $1000)");
            userDepositAmount = scanner.nextLine();
        }

        System.out.println("\nDepositing " + userDepositAmount);
        currentUser.depositFunds(new BigDecimal(userDepositAmount));
    }

    public static void withdraw() {
        System.out.println("\nWithdraw funds from your account.\n");
        System.out.println("How much would you like to withdraw?");
        String userWithdrawAmount = scanner.nextLine();

        while (!isValidTransactionAmount(userWithdrawAmount)) {
            System.out.println("Please enter a valid amount. ($1 to $1000)");
            userWithdrawAmount = scanner.nextLine();
        }

        System.out.println("\nWithdrawing " + userWithdrawAmount);
        currentUser.withdrawFunds(new BigDecimal(userWithdrawAmount));
    }

    /* Determines if a requested transaction amount is valid.
     *  Transactions are limited to anything above $1.00 and under or equal to $1,000 ($0.00 - $1000)
     *  Cent values are optional... E.G. entering 50 instead of 50.00 is valid. */
    public static boolean isValidTransactionAmount(String userTransactionAmount) {
        String REGEX = "^[1-9]$|^[1-9][0-9]{0,2}(?:[.][0-9]{2})?|1000";
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(userTransactionAmount);

        return matcher.matches();
    }

    /* Asks the user if they would like to do anything else after performing an action
     *  If yes, prompt the user to re-authorize by re-entering their pin.
     *  Else, log out the user. */
    public static void continuePrompt() {
        System.out.println("\nWould you like to do anything else?\n(1) Yes\n(2) No");

        String userSelection = scanner.nextLine();
        while (!isValidInput(userSelection, 1)) {
            System.out.println("\nPlease enter a valid selection.");
            userSelection = scanner.nextLine();
        }

        switch(Integer.parseInt(userSelection)) {
            case 1:
                userReAuthentication();
            case 2:
                logout();
            default:
                System.out.println("Please enter a valid selection.");
                continuePrompt();
        }
    }

    /* Re-Authenticate the user if returning to the menu to perform more actions.
     *  If the user enters the wrong pin, count that towards authentication attempts.
     *  If the user attempts to enter a pin 3 times, log them out.*/
    public static void userReAuthentication() {
        System.out.println("\nPlease re-enter your PIN to continue:");
        int accountPin = getLoginInput(4);
        int attempts = 0;

        while (accountPin != currentUser.pin) {
            attempts++;
            if (attempts == 3) logout();
            System.out.println("\nIncorrect PIN. Please re-enter your pin to continue:");
            accountPin = getLoginInput(4);
        }

        mainMenu();
    }


}
