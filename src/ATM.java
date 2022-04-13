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
                break;
            case 2:
                System.out.println("Withdraw");
                break;
            case 3:
                deposit();
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

        System.out.println("Depositing " + userDepositAmount);
    }


}
