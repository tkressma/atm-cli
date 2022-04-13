public class ATM {
    private static AccountDatabase accountDatabase = new AccountDatabase();

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        System.out.println("Good day! Please enter you account details to get started.");
        login();
    }

    public static void login() {
        int acctId = 55555555;
        int acctPin = 1234;

        while (!accountDatabase.authenticateUser(acctId, acctPin)) {
            System.out.println("\nInvalid Credentials. Please re-enter your account ID and Pin.\n");
            // Implement a way for users to enter input and validate that input
            break;
        }
    }

    public static void getLoginInput() {

    }


}
