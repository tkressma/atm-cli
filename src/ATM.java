public class ATM {
    private static CSVReader accountDatabase = new CSVReader();

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
    }

}
