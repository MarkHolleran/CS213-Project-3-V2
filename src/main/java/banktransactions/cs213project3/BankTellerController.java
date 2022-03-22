package banktransactions.cs213project3;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class BankTellerController {


        public static final int OPEN_CHECKING_ARGS = 4;
        public static final int OPEN_C_CHECKING_ARGS = 5;
        public static final int OPEN_SAVINGS_ARGS = 5;
        public static final int OPEN_M_MARKET_ARGS = 4;

        public static final int LOYAL_SAVINGS = 0;
        public static final int NON_LOYAL_SAVINGS = 1;

        public static final int LOYAL = 1;
        public static final int NON_LOYAL = 0;
        public static final int CLOSE_ACCT_ARGS_MIN = 4;
        public static final int NOT_FOUND = -1;
        public static final int INVALID_DEPOSIT_OR_WITHDRAWAL = -1;
        public static final int CAMPUS_CODE_MIN = 0;
        public static final int CAMPUS_CODE_MAX = 2;
        public static final int DEPOSIT_OR_WITHDRAW_NUM_ARGUMENTS = 5;

        public static final int INDEX_0F_CHECKING = 0;
        public static final int INDEX_OF_COLLEGECHECKING = 1;

        public static final int INDEX_0F_SAVINGS = 2;
        public static final int INDEX_OF_MONEY_MARKET = 3;

        public static final int INDEX_OF_CAMDEN = 0;
        public static final int INDEX_OF_NEWARK = 1;
        public static final int INDEX_OF_NB = 2;


        @FXML
        private TextArea accountDatabaseOutput;

        @FXML
        private HBox cheese;

        @FXML
        private ToggleGroup depositWithdrawAccountType;

        @FXML
        private TextField depositWithdrawAmount;

        @FXML
        private DatePicker depositWithdrawDob;

        @FXML
        private TextField depositWithdrawFirstName;

        @FXML
        private TextField depositWithdrawLastName;

        @FXML
        private TextArea depositWithdrawOutput;

        @FXML
        private ToggleGroup openCloseAccountType;

        @FXML
        private RadioButton openCloseCamden;


        @FXML
        private ToggleGroup openCloseCampus;

        @FXML
        private DatePicker openCloseDob;

        @FXML
        private TextField openCloseInitialAccountAmount;

        @FXML
        private TextField openCloseLastName;

        @FXML
        private CheckBox openCloseLoyalCustomer;

        @FXML
        private RadioButton openCloseNB;

        @FXML
        private RadioButton openCloseNewark;

        @FXML
        private TextArea openCloseOutput;

        @FXML
        private TextField openClosefirstName;

        AccountDatabase database = new AccountDatabase();

        @FXML
        void printAllAccounts(ActionEvent event) {

                StringBuilder sb = new StringBuilder();
                if(database.getNumAcct()==0){
                        sb.append("Account Database is empty!\n");
                }else{
                        sb.append("\n");
                        sb.append("*list of accounts in the database*\n");
                        sb.append(database.print());
                        sb.append("*end of list*\n");
                        sb.append("\n");

                }
                accountDatabaseOutput.setText(sb.toString());

        }


        @FXML
        void calculateFeesAndInterest(ActionEvent event) {

                StringBuilder sb = new StringBuilder();
                if(database.getNumAcct()==0){
                        sb.append("Account Database is empty!\n");
                }else{
                        sb.append("\n");
                        sb.append("*list of accounts with fee and monthly interest\n");
                        sb.append(database.printFeeAndInterest());
                }


                accountDatabaseOutput.setText(sb.toString());

        }

        @FXML
        void applyFeesAndInterest(ActionEvent event) {

                StringBuilder sb = new StringBuilder();
                if(database.getNumAcct()==0){
                        sb.append("Account Database is empty!\n");
                }else{
                        sb.append("\n");
                        sb.append("*list of accounts with updated balance\n");
                        database.calculate();
                        sb.append(database.print());
                        sb.append("*end of list*\n");
                        sb.append("\n");
                }


                accountDatabaseOutput.setText(sb.toString());

        }



        @FXML
        void closeAccount(ActionEvent event) {

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
        private Account createAccount(Profile profile, ToggleGroup type, double balance){

                if(depositWithdrawAccountType.getToggles().get(INDEX_0F_CHECKING).isSelected()){
                        return new Checking(profile,balance);
                }
                if(depositWithdrawAccountType.getToggles().get(INDEX_OF_COLLEGECHECKING).isSelected()){
                        return new CollegeChecking(profile,balance,0);
                }
                if(depositWithdrawAccountType.getToggles().get(INDEX_0F_SAVINGS).isSelected()){
                        return new Savings(profile,balance,0);
                }
                if(depositWithdrawAccountType.getToggles().get(INDEX_OF_MONEY_MARKET).isSelected()){
                        return new MoneyMarket(profile,balance);
                }
                return null;
        }

        /**
         * Verifies that the deposit from user input is a positive double
         *
         * If the deposit is invalid, the respective error message is printed
         *
         * @param deposit String from String Tokenizer potentially containing a proper deposit
         * @param initial boolean to signify if this is the account's initial deposit
         *
         * @return String value of the deposit if valid, invalid_deposit otherwise
         */
        private String validDeposit(String deposit, boolean initial){
                StringBuilder sb = new StringBuilder();
                double balance;
                try{
                        balance = Double.parseDouble(deposit);
                        if(balance <= 0){
                                if(initial){
                                        sb.append("Initial deposit cannot be 0 or negative.");
                                }else{
                                        sb.append("Deposit - amount cannot be 0 or negative.");
                                }
                                //return INVALID_DEPOSIT_OR_WITHDRAWAL;
                                return sb.toString();
                        }else{
                                return deposit;
                        }
                }catch(Exception e) {
                        sb.append("Not a valid amount.");
                        //return INVALID_DEPOSIT_OR_WITHDRAWAL;
                        return sb.toString();
                }
        }

        /**
         * Verifies that the requested withdrawal from user input is a positive double
         *
         * If the withdrawal is invalid, the respective error message is printed
         *
         * @param deposit String from String Tokenizer potentially containing a proper withdrawal
         *
         * @return String value of the withdrawal if valid, invalid_deposit otherwise
         */
        private String validWithdraw(String deposit){
                StringBuilder sb = new StringBuilder();
                double balance;
                try{
                        balance = Double.parseDouble(deposit);
                        if(balance <= 0){
                                sb.append("Withdraw - amount cannot be 0 or negative.");
                                //return INVALID_DEPOSIT_OR_WITHDRAWAL;
                                return sb.toString();
                        }else{
                                return deposit;
                        }
                }catch(Exception e) {
                        sb.append("Not a valid amount.");
                        return sb.toString();
                }
        }

        
        @FXML
        void depositAmount(ActionEvent event) {

                //String dbDate = depositWithdrawDob.getValue().toString();

                Date newDate = new Date("3/21/2001");
                //Date newDate = new Date(dbDate);

                Profile newProfile = new Profile(depositWithdrawFirstName.getText(), depositWithdrawLastName.getText(), newDate);

                double deposit = Double.parseDouble(depositWithdrawAmount.getText());

                Account accot = createAccount(newProfile, depositWithdrawAccountType,0);

                if (depositWithdrawAccountType.getToggles().get(INDEX_0F_CHECKING).isSelected()){

                        if(!database.findAcct(accot)){
                                depositWithdrawOutput.setText(newProfile.toString() + " " + "Checking" + " is not in the database.");
                        }else{
                                boolean initialDeposit = false;

                                if(!validDeposit(depositWithdrawAmount.getText(),
                                        initialDeposit).equals("Deposit - amount cannot be 0 or negative.") ||
                                        !validDeposit(depositWithdrawAmount.getText(),
                                        initialDeposit).equals("Not a valid amount.")){
                                        Account acct = createAccount(newProfile, depositWithdrawAccountType, deposit);
                                        database.deposit(acct);
                                        depositWithdrawOutput.setText("Deposit - balance updated.");
                                }else{
                                        depositWithdrawOutput.setText(validDeposit(depositWithdrawAmount.getText(), initialDeposit));
                                }
                        }

                }else if (depositWithdrawAccountType.getToggles().get(INDEX_OF_COLLEGECHECKING).isSelected()){

                        if(!database.findAcct(accot)){
                                depositWithdrawOutput.setText(newProfile.toString() + " " + "College Checking" + " is not in the database.");
                        }else{
                                boolean initialDeposit = false;

                                if(!validDeposit(depositWithdrawAmount.getText(),
                                        initialDeposit).equals("Deposit - amount cannot be 0 or negative.") ||
                                        !validDeposit(depositWithdrawAmount.getText(),
                                                initialDeposit).equals("Not a valid amount.")){
                                        Account acct = createAccount(newProfile, depositWithdrawAccountType, deposit);
                                        database.deposit(acct);
                                        depositWithdrawOutput.setText("Deposit - balance updated.");
                                }else{
                                        depositWithdrawOutput.setText(validDeposit(depositWithdrawAmount.getText(), initialDeposit));
                                }
                        }
                }else if (depositWithdrawAccountType.getToggles().get(INDEX_0F_SAVINGS).isSelected()){

                        if(!database.findAcct(accot)){
                                depositWithdrawOutput.setText(newProfile.toString() + " " + "Savings" + " is not in the database.");
                        }else{
                                boolean initialDeposit = false;

                                if(!validDeposit(depositWithdrawAmount.getText(),
                                        initialDeposit).equals("Deposit - amount cannot be 0 or negative.") ||
                                        !validDeposit(depositWithdrawAmount.getText(),
                                                initialDeposit).equals("Not a valid amount.")){
                                        Account acct = createAccount(newProfile, depositWithdrawAccountType, deposit);
                                        database.deposit(acct);
                                        depositWithdrawOutput.setText("Deposit - balance updated.");
                                }else{
                                        depositWithdrawOutput.setText(validDeposit(depositWithdrawAmount.getText(), initialDeposit));
                                }
                        }
                }else if (depositWithdrawAccountType.getToggles().get(INDEX_OF_MONEY_MARKET).isSelected()){

                        if(!database.findAcct(accot)){
                                depositWithdrawOutput.setText(newProfile.toString() + " " + "Money Market" + " is not in the database.");
                        }else{
                                boolean initialDeposit = false;

                                if(!validDeposit(depositWithdrawAmount.getText(),
                                        initialDeposit).equals("Deposit - amount cannot be 0 or negative.") ||
                                        !validDeposit(depositWithdrawAmount.getText(),
                                                initialDeposit).equals("Not a valid amount.")){
                                        Account acct = createAccount(newProfile, depositWithdrawAccountType, deposit);
                                        database.deposit(acct);
                                        depositWithdrawOutput.setText("Deposit - balance updated.");
                                }else{
                                        depositWithdrawOutput.setText(validDeposit(depositWithdrawAmount.getText(), initialDeposit));
                                }
                        }
                }
                //depositWithdrawOutput.setText("Not a valid amount.");
                // we need different one
        }


        @FXML
        void openAccount(ActionEvent event) {


                //is run when Open button is pressed
                //we want to take all of the field information and create a profile object
                //then put it in the database

                Date newDate = new Date(openCloseDob.toString());

                Profile newProfile = new Profile(openClosefirstName.getText(), openCloseLastName.getText(), newDate);

                double initAccountAmount = Integer.parseInt(openCloseInitialAccountAmount.getText());

                //think indexs goes from left to right
                //0 1
                //2 3

                //checking
                //college checking
                //savings
                //money maket

                //also have to account for deposits that dont meet account opening thresholds etcc..

                //need to figure out how to gray out buttons

                if (openCloseAccountType.getToggles().get(INDEX_0F_CHECKING).isSelected() == true){

                        openCloseCamden.setDisable(true);
                        openCloseNewark.setDisable(true);
                        openCloseNB.setDisable(true);
                        openCloseLoyalCustomer.setDisable(true);

                        Checking newChecking = new Checking(newProfile, initAccountAmount);
                        database.open(newChecking);
                        openCloseOutput.appendText("Account opened");


                }else if (openCloseAccountType.getToggles().get(INDEX_OF_COLLEGECHECKING).isSelected() == true){

                        openCloseLoyalCustomer.setDisable(true);

                        if (openCloseCampus.getToggles().get(INDEX_OF_CAMDEN).isSelected() == true){

                                CollegeChecking newCollegeChecking = new CollegeChecking(newProfile, initAccountAmount, INDEX_OF_CAMDEN);
                                database.open(newCollegeChecking);
                                openCloseOutput.setText("Account opened");


                        }else if (openCloseCampus.getToggles().get(INDEX_OF_NEWARK).isSelected() == true){

                                CollegeChecking newCollegeChecking = new CollegeChecking(newProfile, initAccountAmount, INDEX_OF_NEWARK);
                                database.open(newCollegeChecking);
                                openCloseOutput.setText("Account opened");


                        }else {

                                CollegeChecking newCollegeChecking = new CollegeChecking(newProfile, initAccountAmount, INDEX_OF_NB);
                                database.open(newCollegeChecking);
                                openCloseOutput.setText("Account opened");


                        }

                }else if (openCloseAccountType.getToggles().get(INDEX_0F_SAVINGS).isSelected() == true){
                        //can have or not have loyalcustomer checked

                        openCloseLoyalCustomer.setSelected(false);
                        //I think this will set it to uncheck when ever we click from
                        //savings to another type then back to savings

                        openCloseCamden.setDisable(true);
                        openCloseNewark.setDisable(true);
                        openCloseNB.setDisable(true);

                        if (openCloseLoyalCustomer.isSelected() == false){

                                Savings newSavings = new Savings(newProfile, initAccountAmount,NON_LOYAL_SAVINGS);
                                //idk why nonloyal savings is 1 i thought it was 0?
                                database.open(newSavings);

                                openCloseOutput.setText("Account opened");


                        }else{
                                Savings newSavings = new Savings(newProfile, initAccountAmount,LOYAL_SAVINGS);
                                database.open(newSavings);
                                openCloseOutput.setText("Account opened");
                        }




                }else if (openCloseAccountType.getToggles().get(INDEX_OF_MONEY_MARKET).isSelected() == true){
                        //loyal by default
                        openCloseLoyalCustomer.setSelected(true);

                        if (openCloseLoyalCustomer.isSelected() == false){

                                MoneyMarket newMoneyMarket = new MoneyMarket(newProfile, initAccountAmount);
                                //if
                                newMoneyMarket.loyalCustomer = NON_LOYAL;

                                database.open(newMoneyMarket);

                                openCloseOutput.setText("Account opened");


                        }else{

                                MoneyMarket newMoneyMarket = new MoneyMarket(newProfile, initAccountAmount);

                                database.open(newMoneyMarket);

                                openCloseOutput.setText("Account opened");


                        }

                }



        }


        @FXML
        void openCloseCheckingSelected(MouseEvent event) {

                openCloseLoyalCustomer.setSelected(false);

                openCloseNB.setSelected(false);
                openCloseCamden.setSelected(false);
                openCloseNewark.setSelected(false);

                openCloseCamden.setDisable(true);
                openCloseNewark.setDisable(true);
                openCloseNB.setDisable(true);

                openCloseLoyalCustomer.setDisable(true);




        }

        @FXML
        void openCloseCollegeCheckingSelected(MouseEvent event) {

                openCloseLoyalCustomer.setSelected(false);

                openCloseCamden.setDisable(false);
                openCloseNewark.setDisable(false);
                openCloseNB.setDisable(false);

                openCloseLoyalCustomer.setDisable(true);

        }

        @FXML
        void openCloseMoneyMarketSelected(MouseEvent event) {

                openCloseNB.setSelected(false);
                openCloseCamden.setSelected(false);
                openCloseNewark.setSelected(false);

                openCloseCamden.setDisable(true);
                openCloseNewark.setDisable(true);
                openCloseNB.setDisable(true);
                openCloseLoyalCustomer.setSelected(true);
                openCloseLoyalCustomer.setDisable(true);



        }

        @FXML
        void openCloseSavingsSelected(MouseEvent event) {

                openCloseLoyalCustomer.setSelected(false);

                openCloseNB.setSelected(false);
                openCloseCamden.setSelected(false);
                openCloseNewark.setSelected(false);

                openCloseCamden.setDisable(true);
                openCloseNewark.setDisable(true);
                openCloseNB.setDisable(true);
                openCloseLoyalCustomer.setDisable(false);

        }


        @FXML
        void printAccountsByType(ActionEvent event) {

                StringBuilder sb = new StringBuilder();
                if(database.getNumAcct()==0){
                        sb.append("Account Database is empty!\n");
                }else{
                        sb.append("\n");
                        sb.append("*list of accounts by account type.\n");
                        sb.append(database.printByAccountType());
                }


                accountDatabaseOutput.setText(sb.toString());

        }





}
