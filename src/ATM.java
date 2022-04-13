import java.util.Scanner;

public class ATM {
    private static AccountDatabase accountDatabase = new AccountDatabase();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        System.out.println("Good day! Please enter you account details to get started.");
        login();
    }

    public static void login() {
        int acctId = getLoginInput(8);
        int acctPin = getLoginInput(4);

        while (!accountDatabase.authenticateUser(acctId, acctPin)) {
            System.out.println("\nInvalid Credentials. Please re-enter your account ID and Pin.\n");
            acctId = getLoginInput(8);
            acctPin = getLoginInput(4);
        }
        
    }

    public static int getLoginInput(int inputLength) {
        String inputType = inputLength == 8 ? "ID" : "PIN";

        System.out.printf("Enter your %s: ", inputType);
        String userInput = scanner.nextLine();

        return Integer.parseInt(userInput);
    }


}
