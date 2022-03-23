package banktransactions.cs213project3;
import java.util.Scanner;
import java.util.StringTokenizer;
/**
 * Class that represents the commandline interface
 * User input is tokenized and separated to
 * verify a correct input has been entered.
 * Methods in this class allow the user to
 * create an account, close an account, deposit money
 * into an account, withdraw money from an account,
 * display all accounts in the database with
 * various sorting methods, and a quit command
 *
 * @author Mark Holleran, Abhitej Bokka
 */
public class BankTeller {

    public static final int OPEN_CHECKING_ARGS = 4;
    public static final int OPEN_C_CHECKING_ARGS = 5;
    public static final int OPEN_SAVINGS_ARGS = 5;
    public static final int OPEN_M_MARKET_ARGS = 4;
    public static final int LOYAL_SAVINGS = 0;
    public static final int NON_LOYAL_SAVINGS = 1;
    public static final int CLOSE_ACCT_ARGS_MIN = 4;
    public static final int NOT_FOUND = -1;
    public static final int INVALID_DEPOSIT_OR_WITHDRAWAL = -1;
    public static final int CAMPUS_CODE_MIN = 0;
    public static final int CAMPUS_CODE_MAX = 2;
    public static final int DEPOSIT_OR_WITHDRAW_NUM_ARGUMENTS = 5;

    /**
     * Constructor for Creating 'Bankteller
     */
    public BankTeller (){

    }

    /**
     * Verifies that the deposit from user input is a positive double
     *
     * If the deposit is invalid, the respective error message is printed
     *
     * @param deposit String from String Tokenizer potentially containing a proper deposit
     * @param initial boolean to signify if this is the account's initial deposit
     *
     * @return Double value of the deposit if valid, invalid_deposit otherwise
     */
    private double validDeposit(String deposit, boolean initial){
        double balance;
        try{
            balance = Double.parseDouble(deposit);
            if(balance <= 0){
                if(initial){
                    System.out.println("Initial deposit cannot be 0 or negative.");
                }else{
                    System.out.println("Deposit - amount cannot be 0 or negative.");
                }
                return INVALID_DEPOSIT_OR_WITHDRAWAL;
            }else{
                return balance;
            }
        }catch(Exception e) {
            System.out.println("Not a valid amount.");
            return INVALID_DEPOSIT_OR_WITHDRAWAL;
        }
    }

    /**
     * Verifies that the requested withdrawal from user input is a positive double
     *
     * If the withdrawal is invalid, the respective error message is printed
     *
     * @param deposit String from String Tokenizer potentially containing a proper withdrawal
     *
     * @return Double value of the withdrawal if valid, invalid_deposit otherwise
     */
    private double validWithdraw(String deposit){
        double balance;
        try{
            balance = Double.parseDouble(deposit);
            if(balance <= 0){
                System.out.println("Withdraw - amount cannot be 0 or negative.");
                return INVALID_DEPOSIT_OR_WITHDRAWAL;
            }else{
                return balance;
            }
        }catch(Exception e) {
            System.out.println("Not a valid amount.");
            return INVALID_DEPOSIT_OR_WITHDRAWAL;
        }
    }


    /**
     * Private method that attempts the Open Account command
     *
     * If the command follows the proper formatting,
     * the command will move forward to execute
     * commands of different account type.
     *
     * @param segmentedInput Commandline input from user
     * @param database Database containing Account objects
     */
    private void tryCommandO(StringTokenizer segmentedInput, AccountDatabase database){
        try {
            boolean executed = false;
            switch (segmentedInput.nextToken()){
                case "C":
                    if (segmentedInput.countTokens() == OPEN_CHECKING_ARGS){
                        executeCommandCaseC(segmentedInput, database);
                        executed = true;
                    }
                    break;
                case "CC":
                    if (segmentedInput.countTokens() == OPEN_C_CHECKING_ARGS){
                        executeCommandCaseCC(segmentedInput, database);
                        executed = true;
                    }
                    break;
                case "S":
                    if (segmentedInput.countTokens() == OPEN_SAVINGS_ARGS){
                        executeCommandCaseS(segmentedInput, database);
                        executed = true;
                    }
                    break;
                case "MM":
                    if (segmentedInput.countTokens() == OPEN_M_MARKET_ARGS){
                        executeCommandCaseMM(segmentedInput, database);
                        executed = true;
                    }
                    break;
                default :
                    System.out.println("Missing data for opening an account.");
            }
            if(!executed) System.out.println("Missing data for opening an account.");
        } catch (Exception e) {
            System.out.println("Invalid Command!");
        }
    }


    /**
     * Method that constructs a Profile Object
     *
     * Takes in a StringTokenizer containing the
     * first name, last name, and date of birth
     * and constructs a Profile object
     *
     * @param segmentedInput String Tokenizer from user input
     */
    private Profile createProfile(StringTokenizer segmentedInput){
        String fName = segmentedInput.nextToken();
        String lName = segmentedInput.nextToken();
        String dob = segmentedInput.nextToken();
        Date date = new Date(dob);
        Profile profile = new Profile(fName, lName, date);

        if(!date.isValid()){
            System.out.println("Date of birth invalid.");
            return null;
        }
        return profile;
    }


    /**
     * Attempts to reopen an account
     *
     * If the specified account already exists and is closed,
     * open() from AccountDatabase will reopen the account, otherwise
     * a message saying the account is open is printed
     *

     *
     * @param checking Account object to be opened in array
     * @param profile Profile used to print eror message if already open
     * @param database AccountDatabase to access the array of accounts
     */
    private void attemptReopen(Account checking, Profile profile, AccountDatabase database){
        if(database.getAccount(checking).closed){

            checking.closed = true;
            database.open(checking);
            System.out.println("Account reopened.");
        }else{
            checking.closed = false;
            System.out.println(profile.toString()+ " same account(type) is in the database.");
        }
    }

    /**
     * Attempts to open an account unless a duplicate is found
     *
     *
     * @param checking Account object to be opened in array
     * @param profile Profile used to print eror message if already open
     * @param database AccountDatabase to access the array of accounts
     */
    private void attemptOpen(Account checking, Profile profile, AccountDatabase database){
        try{
            if(database.duplicateAccount(checking)){
                System.out.println(profile.toString()+ " same account(type) is in the database.");
                return;
            }
        }catch(Exception e){}
        database.open(checking);
        System.out.println("Account opened.");
    }

    /**
     * Private method for creating a Checking Account object
     * @param segmentedInput Commandline input containing first name, last name, date of birth, and initial deposit amount
     * @param database Database containing Account objects
     */
    private void executeCommandCaseC(StringTokenizer segmentedInput, AccountDatabase database){

        Profile profile = createProfile(segmentedInput);
        String deposit = segmentedInput.nextToken();

        boolean initialDeposit = true;
        if(validDeposit(deposit, initialDeposit)!=INVALID_DEPOSIT_OR_WITHDRAWAL){
            Checking checking = new Checking(profile, validDeposit(deposit, initialDeposit));
            if(database.findAcct(checking)){
                attemptReopen(checking, profile, database);
            }else{
                attemptOpen(checking, profile, database);
            }

        }
    }

    /**
     * Private method for creating a College Checking Account object
     * @param segmentedInput String containing first name, last name, date of birth, initial deposit, and campus code
     * @param database Database containing Account objects
     */
    private void executeCommandCaseCC(StringTokenizer segmentedInput, AccountDatabase database){
        Profile profile = createProfile(segmentedInput);

        String deposit = segmentedInput.nextToken();
        String campus = segmentedInput.nextToken();
        int campusCode;

        try{
            campusCode = Integer.parseInt(campus);
            if(campusCode < CAMPUS_CODE_MIN || campusCode > CAMPUS_CODE_MAX){
                System.out.println("Invalid campus code.");
                return;
            }
        }catch(Exception e){
            System.out.println("Invalid campus code.");
            return;
        }
        boolean initialDeposit = true;
        if(validDeposit(deposit, initialDeposit)!=INVALID_DEPOSIT_OR_WITHDRAWAL){

            CollegeChecking checking = new CollegeChecking(profile, validDeposit(deposit, initialDeposit), campusCode);
            if(database.findAcct(checking)){
                attemptReopen(checking, profile, database);
            }else{
                attemptOpen(checking, profile, database);
            }

        }
    }

    /**
     * Private method for creating a Savings Account object
     * @param segmentedInput String containing first name, last name, date of birth, inital deposit, and customer loyalty
     * @param database Database containing Account objects
     */
    private void executeCommandCaseS(StringTokenizer segmentedInput, AccountDatabase database){
        Profile profile = createProfile(segmentedInput);
        String deposit = segmentedInput.nextToken();
        String loyalty = segmentedInput.nextToken();
        int loyaltyCode;

        try{
            loyaltyCode = Integer.parseInt(loyalty);
            if(!(loyaltyCode == NON_LOYAL_SAVINGS || loyaltyCode == LOYAL_SAVINGS)){
                System.out.println("Invalid loyalty code.");
                return;
            }
        }catch(Exception e){
            System.out.println("Invalid loyalty code.");
            return;
        }

        boolean initialDeposit = true;
        if(validDeposit(deposit, initialDeposit)!=INVALID_DEPOSIT_OR_WITHDRAWAL){
            Savings savings = new Savings(profile, validDeposit(deposit, initialDeposit), loyaltyCode);
            boolean attempt = false;
            if(savings.closed){
                attempt = true;
            }
            if(!database.open(savings)){
                System.out.println(profile.toString()+ " same account(type) is in the database.");
                return;
            }
            if(database.findAcct(savings) && attempt){
                System.out.println("Account reopened.");
            }else{
                System.out.println("Account opened.");
            }

        }
    }

    /**
     * Private method for creating a Money Market Account object
     * @param segmentedInput String containing first name, last name, date of birth, and initial deposit
     * @param database Database containing Account objects
     */
    private void executeCommandCaseMM(StringTokenizer segmentedInput, AccountDatabase database){
        String fName = segmentedInput.nextToken();
        String lName = segmentedInput.nextToken();
        String dob = segmentedInput.nextToken();
        String deposit = segmentedInput.nextToken();

        Date date = new Date(dob);

        if(!date.isValid()){
            System.out.println("Date of birth invalid.");
            return;
        }
        boolean initialDeposit = true;
        if(validDeposit(deposit, initialDeposit)!=INVALID_DEPOSIT_OR_WITHDRAWAL){
            Profile profile = new Profile(fName, lName, date);
            MoneyMarket checking = new MoneyMarket(profile, validDeposit(deposit, initialDeposit));

            if(validDeposit(deposit, initialDeposit)<2500){
                System.out.println("Minimum of $2500 to open a MoneyMarket account.");
                return;
            }

            if(database.findAcct(checking)&& database.getAccount(checking).getType().equals(checking.getType())){
                attemptReopen(checking, profile, database);
            }else{
                attemptOpen(checking, profile, database);
            }
        }

    }

    /**
     * Matches the command selecting an account and returns its respective account name
     *
     * @param cmd String from user input to identify what type of account is being selected
     *
     * @return String containing the account type if matched, returns No Match otherwise
     */
    private String getAccountType(String cmd){
        if(cmd.equals("C")){
            return "Checking";
        }
        if(cmd.equals("CC")){
            return "College Checking";
        }
        if(cmd.equals("S")){
            return "Savings";
        }
        if(cmd.equals("MM")){
            return "Money Market";
        }
        return "No Match";
    }

    /**
     * Private method for Closing an account
     *
     * @param segmentedInput Commandline input for closing an Account
     * @param database Database containing Account objects
     */
        private void tryCommandC(StringTokenizer segmentedInput, AccountDatabase database){
        if (segmentedInput.countTokens() == CLOSE_ACCT_ARGS_MIN) {
            try {

                String accountType = segmentedInput.nextToken();
                String fName = segmentedInput.nextToken();
                String lName = segmentedInput.nextToken();
                String dob = segmentedInput.nextToken();
                Date date = new Date(dob);
                Profile profile = new Profile(fName, lName, date);

                Account acct = createAccount(profile, accountType, 0);

                if(database.findCertain(acct) != NOT_FOUND){
                    int index = database.findCertain(acct);

                    if(database.alreadyClosed(index)){
                        database.close(acct);
                        System.out.println("Account is closed already.");
                    }else{
                        database.close(acct);
                        System.out.println("Account closed.");
                    }
                }


            } catch (Exception e) {
                System.out.println("Missing data for closing an account.");
            }
        } else {
            System.out.println("Missing data for closing an account.");
        }
    }

    /**
     * Creates an account based on the account type given a profile and balance
     *
     * @param profile Profile object that holds first name, last name, and date of birth
     * @param type String from user input to identify what type of account is being selected
     * @param balance Double value representing account balance
     *
     * @return an Account of either type Checking, College Checking, Savings, or Money Market
     */
    private Account createAccount(Profile profile, String type, double balance){

        if(type.equals("C")){
            return new Checking(profile,balance);
        }
        if(type.equals("CC")){
            return new CollegeChecking(profile,balance,0);
        }
        if(type.equals("S")){
            return new Savings(profile,balance,0);
        }
        if(type.equals("MM")){
            return new MoneyMarket(profile,balance);
        }
        return null;
    }

    /**
     * Private method for depositing amount into an Account object
     *
     * @param segmentedInput Commandline input containing account type,
     * first and last name, date of birth, and deposit amount
     * @param database Database containing Account objects
     */
    private void tryCommandD(StringTokenizer segmentedInput, AccountDatabase database){
        if (segmentedInput.countTokens() == DEPOSIT_OR_WITHDRAW_NUM_ARGUMENTS) {
            try {

                String accountType = segmentedInput.nextToken();
                Profile profile = createProfile(segmentedInput);
                String deposit = segmentedInput.nextToken();
                String type = getAccountType(accountType);
                Account accot = createAccount(profile, accountType,0);

                if(!database.findAcct(accot)){
                    System.out.println(profile.toString() + " " + type + " is not in the database.");
                    return;
                }

                boolean initialDeposit = false;

                if(validDeposit(deposit, initialDeposit) != INVALID_DEPOSIT_OR_WITHDRAWAL){
                    Account acct = createAccount(profile, accountType, validDeposit(deposit, initialDeposit));
                    database.deposit(acct);
                    System.out.println("Deposit - balance updated.");
                }

            } catch (Exception e) {
                System.out.println("Missing data for closing an account.");
            }
        } else {
            System.out.println("Missing data for closing an account.");
        }
    }

    /**
     * Private method for subtracting specified
     * amount from the balance of an Account object
     *
     * @param segmentedInput String input containing account type,
     * first and last name, date of birth and withdraw amount
     * @param database Database containing Account objects
     */
    private void tryCommandW(StringTokenizer segmentedInput, AccountDatabase database){
        if (segmentedInput.countTokens() == DEPOSIT_OR_WITHDRAW_NUM_ARGUMENTS) {
            try {

                String accountType = segmentedInput.nextToken();
                Profile profile = createProfile(segmentedInput);
                String deposit = segmentedInput.nextToken();
                String type = getAccountType(accountType);
                Account accot = createAccount(profile, accountType,0);

                if(!database.findAcct(accot)){
                    System.out.println(profile.toString() + " " + type + " is not in the database.");
                    return;
                }

                if(validWithdraw(deposit) != INVALID_DEPOSIT_OR_WITHDRAWAL){
                    Account acct = createAccount(profile, accountType, validWithdraw(deposit));
                    if(database.withdraw(acct)){
                        System.out.println("Withdraw - balance updated.");
                    }else{
                        System.out.println("Withdraw - insufficient fund.");
                    }
                }

            } catch (Exception e) {
                System.out.println("Missing data for closing an account.");
            }
        } else {
            System.out.println("Missing data for closing an account.");
        }
    }

    /**
     * Private method trying to execute available commands
     *
     * String input is tokenized and processed one at a time
     * @param segmentedInput String input broken down and
     * @param database Database containing Account objects
     */
    private void parseCommands(StringTokenizer segmentedInput, AccountDatabase database){
        switch (segmentedInput.nextToken()){
            case "O":
                tryCommandO(segmentedInput, database);
                break;
            case "C":
                tryCommandC(segmentedInput, database);
                break;
            case "D":
                tryCommandD(segmentedInput, database);
                break;
            case "W":
                tryCommandW(segmentedInput, database);
                break;
            case "P":
                executeCommandP(database);
                break;
            case "PT":
                executeCommandPT(database);
                break;
            case "PI":
                executeCommandPI(database);
                break;
            case "UB":
                executeCommandUB(database);
                break;
            case "Q":
                break;
            default :
                System.out.println("Invalid Command!");
        }

    }

    /**
     * Private method that displays all accounts in the Database Array
     * in current ordering
     *
     * @param database Database containing Account objects
     */
    private void executeCommandP(AccountDatabase database){
        if(database.getNumAcct()==0){
            System.out.println("Account Database is empty!");
            return;
        }
        System.out.println();
        System.out.println("*list of accounts in the database*");
        database.print();
        System.out.println("*end of list*");
        System.out.println();
    }

    /**
     * Private method that displays all accounts in the Database Array
     * based on the type of account
     *
     * @param database Database containing Account objects
     */
    private void executeCommandPT(AccountDatabase database){
        if(database.getNumAcct()==0){
            System.out.println("Account Database is empty!");
            return;
        }
        System.out.println();
        System.out.println("*list of accounts by account type.");
        database.printByAccountType();
    }

    /**
     * Private method that displays all accounts in the Database Array
     * in current ordering with fees and monthlyInterest
     *
     * @param database Database containing Account objects
     */
    private void executeCommandPI(AccountDatabase database){
        if(database.getNumAcct()==0){
            System.out.println("Account Database is empty!");
            return;
        }
        System.out.println();
        System.out.println("*list of accounts with fee and monthly interest");
        database.printFeeAndInterest();
    }

    /**
     * Private method that recalculates balances given fees and
     * monthlyInterst and displays all accounts in the Database Array
     * in current ordering
     *
     * @param database Database containing Account objects
     */
    private void executeCommandUB(AccountDatabase database){
        if(database.getNumAcct()==0){
            System.out.println("Account Database is empty!");
            return;
        }
        System.out.println();
        System.out.println("*list of accounts with updated balance");
        database.calculate();
        database.print();
        System.out.println("*end of list.");
        System.out.println();
    }

    /**
     * Method that runs the BankTeller commandline
     * input until Q is pressed.
     */
    public void run(){

        Scanner input = new Scanner(System.in);

        AccountDatabase database = new AccountDatabase();
        System.out.println("Bank Teller is running");
        String commandInput = input.nextLine();

        while (!("Q").equals(commandInput)){

            if(!("").equals(commandInput)) {
                if(commandInput.contains("\t")){
                    commandInput = commandInput.replace("\t","");
                }
                StringTokenizer segmentedInput = new StringTokenizer(commandInput, " ");
                parseCommands(segmentedInput, database);
            }
            commandInput = input.nextLine();

        }

        System.out.println("Bank Teller is terminated. ");
        System.exit(0);

    }

}