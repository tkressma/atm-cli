import java.math.BigDecimal;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ATM {
    private static AccountDatabase accountDatabase = new AccountDatabase();
    private static Scanner scanner = new Scanner(System.in);
    private static Account currentUser;
    static final int PIN_LENGTH = 4;
    static final int ID_LENGTH = 8;

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        System.out.println("Good day! Please enter you account details to get started.");
        login();
        System.out.println("\nHello, " + currentUser.getAccountFirstName() + "!");
        mainMenu();
    }

    private static void login() {
        int accountId = getLoginInput(ID_LENGTH);
        int accountPin = getLoginInput(PIN_LENGTH);

        while (!accountDatabase.authenticateUser(accountId, accountPin)) {
            System.out.println("\nInvalid Credentials. Please re-enter your account ID and Pin.\n");
            accountId = getLoginInput(ID_LENGTH);
            accountPin = getLoginInput(PIN_LENGTH);
        }
        currentUser = accountDatabase.getAccount(accountId);
    }

    private static int getLoginInput(int inputLength) {
        String inputType = inputLength == ID_LENGTH ? "ID" : "PIN";

        System.out.printf("Enter your %s: ", inputType);

        String userInput = scanner.nextLine();

        while (!isValidInput(userInput, inputLength)) {
            System.out.printf("Invalid %s (Must be %d digits). Try again: ", inputType, inputLength);
            userInput = scanner.nextLine();
        }

        return Integer.parseInt(userInput);
    }

    private static void logout() {
        System.out.println("\nThank you for using our bank. Have a wonderful day!\n");
        currentUser = null;
        start();
    }

    // Determines whether the user input is valid based on ID/PIN length.
    private static boolean isValidInput(String userInput, int inputLength) {
        String REGEX = "\\d{" + inputLength + "}";
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(userInput);

        return matcher.matches();
    }

    private static void mainMenu() {
        System.out.println("\nEnter the number relating to the selection you would like to make:");
        System.out.println("(1) - View Balance");
        System.out.println("(2) - Withdraw Funds");
        System.out.println("(3) - Deposit Funds");
        System.out.println("(4) - Exit");

        int userSelection = getUserSelection(1);

        switch (userSelection) {
            case 1:
                System.out.println("\nAccount Balance\nYour balance is: $" + currentUser.getAccountBalance());
                continuePrompt();
            case 2:
                withdraw();
                continuePrompt();
            case 3:
                deposit();
                continuePrompt();
            case 4:
                logout();
            default:
                System.out.println("Please enter a valid selection.");
                mainMenu();
        }
    }

    // Determines if user input is valid then returns it as an integer
    private static int getUserSelection(int inputLength) {
        String userSelection = scanner.nextLine();

        while (!isValidInput(userSelection, inputLength)) {
            System.out.println("Please enter a valid selection.");
            userSelection = scanner.nextLine();
        }

        return Integer.parseInt(userSelection);
    }

    private static void deposit() {
        System.out.println("\nDeposit funds into your account.\n");
        System.out.println("How much would you like to deposit?");
        String userDepositAmount = scanner.nextLine();

        while (!isValidTransactionAmount(userDepositAmount)) {
            userDepositAmount = scanner.nextLine();
        }

        finalizeTransaction("deposit", userDepositAmount);
    }

    private static void withdraw() {
        if (currentUser.accountBalance.compareTo(new BigDecimal(0.99)) <= 0) {
            System.out.println("\nYour account balance does not allow you to withdraw funds at this time.\nReturning to main menu...");
            mainMenu();
        }

        System.out.println("\nWithdraw funds from your account.\n");
        System.out.println("How much would you like to withdraw?");
        String userWithdrawAmount = scanner.nextLine();

        while (!isValidTransactionAmount(userWithdrawAmount)) {
            userWithdrawAmount = scanner.nextLine();
        }

        while (!isValidWithdrawal(userWithdrawAmount)) {
            System.out.printf("You do not have enough funds in your account to withdraw that amount. Please enter another number: ");
            userWithdrawAmount = scanner.nextLine();
        }

        finalizeTransaction("withdraw", userWithdrawAmount);
    }

    private static void finalizeTransaction(String transactionType, String transactionAmount) {
        System.out.printf("\n%s %s...", transactionType.equals("deposit") ? "Depositing" : "Withdrawing", transactionAmount);

        if (transactionType.equals("deposit")) {
            currentUser.depositFunds(new BigDecimal(transactionAmount));
        } else {
            currentUser.withdrawFunds(new BigDecimal(transactionAmount));
        }

        accountDatabase.updateDatabase(currentUser);
        System.out.printf("\nTransaction Complete. Your new balance is %s \n", currentUser.accountBalance.toString());
    }

    // Transactions are valid if above $1.00 and under or equal to $1,000 ($0.00 - $1000). Cent values optional.
    // This is public for the JUnit test.
    public static boolean isValidTransactionAmount(String userTransactionAmount) {
        String REGEX = "^[1-9]$|^[1-9][0-9]{0,2}(?:[.][0-9]{2})?|1000|1000.00";
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(userTransactionAmount);

        if (!matcher.matches()) {
            System.out.printf("Please enter a valid deposit amount. ");
            System.out.println("All transactions are limited to anything above $1.00 and under $1,000.");
            return false;
        }

        return true;
    }

    // Determines if the user has enough funds in the account to withdraw requested amount
    private static boolean isValidWithdrawal(String userWithdrawalAmount) {
        BigDecimal requestedWithdrawalAmount = new BigDecimal(userWithdrawalAmount);
        int result = currentUser.accountBalance.compareTo(requestedWithdrawalAmount);
        return result >= 0;
    }

    private static void continuePrompt() {
        System.out.println("\nWould you like to do anything else?\n(1) Yes\n(2) No");

        int userSelection = getUserSelection(1);

        switch (userSelection) {
            case 1:
                userReAuthentication();
            case 2:
                logout();
            default:
                System.out.println("Please enter a valid selection.");
                continuePrompt();
        }
    }

    private static void userReAuthentication() {
        System.out.println("\nPlease re-enter your PIN to continue:");
        int accountPin = getLoginInput(PIN_LENGTH);
        int attempts = 0;

        while (accountPin != currentUser.accountPin) {
            attempts++;
            if (attempts == 3) logout();
            System.out.println("\nIncorrect PIN. Please re-enter your pin to continue:");
            accountPin = getLoginInput(PIN_LENGTH);
        }

        mainMenu();
    }
}
