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

import java.util.StringTokenizer;

public class BankTellerController {


        public static final int OPEN_CHECKING_ARGS = 4;
        public static final int OPEN_C_CHECKING_ARGS = 5;
        public static final int OPEN_SAVINGS_ARGS = 5;
        public static final int OPEN_M_MARKET_ARGS = 4;
        public static final String ERROR_CONTAINING_DEPOSIT_OR_WITHDRAWAL = "a";


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

        public static final int INDEX_OF_CAMDEN = 2;
        public static final int INDEX_OF_NEWARK = 1;
        public static final int INDEX_OF_NB = 0;


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
        private ToggleGroup openCloseRadioButtons;

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

        @FXML
        private RadioButton openCloseChecking;

        @FXML
        private RadioButton openCloseMoneyMarket;

        @FXML
        private RadioButton openCloseCollegeChecking;

        @FXML
        private RadioButton openCloseSavings;

        @FXML
        private RadioButton closeAccount;

        @FXML
        private RadioButton openAccount;







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

                if(type.getToggles().get(INDEX_0F_CHECKING).isSelected()){
                        return new Checking(profile,balance);
                }
                if(type.getToggles().get(INDEX_OF_COLLEGECHECKING).isSelected()){
                        return new CollegeChecking(profile,balance,0);
                }
                if(type.getToggles().get(INDEX_0F_SAVINGS).isSelected()){
                        return new Savings(profile,balance,0);
                }
                if(type.getToggles().get(INDEX_OF_MONEY_MARKET).isSelected()){
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
                                        sb.append("Initial deposit cannot be 0 or negative.\n");
                                }else{
                                        sb.append("Deposit - amount cannot be 0 or negative.\n");
                                }
                                //return INVALID_DEPOSIT_OR_WITHDRAWAL;
                                return sb.toString();
                        }else{
                                return deposit;
                        }
                }catch(Exception e) {
                        sb.append("Not a valid amount.\n");
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
                                sb.append("Withdraw - amount cannot be 0 or negative\n");
                                return sb.toString();
                        }else{
                                return deposit;
                        }
                }catch(Exception e) {
                        sb.append("Not a valid amount.\n");
                        return sb.toString();
                }
        }


        @FXML
        void withdrawAmount(ActionEvent event) {
            String dbDate = depositWithdrawDob.getValue().toString();
            Date newDate = new Date(dbDate);
                if(!newDate.isValid()){
                        openCloseOutput.setText("Date of birth invalid.");
                }else{
                        Profile newProfile = new Profile(depositWithdrawFirstName.getText(), depositWithdrawLastName.getText(), newDate);
                        Account account = createAccount(newProfile, depositWithdrawAccountType,0);
                        if(!database.findAcct(account)){
                                if (depositWithdrawAccountType.getToggles().get(INDEX_0F_CHECKING).isSelected()){
                                        depositWithdrawOutput.setText(newProfile.toString() + " " + "Checking" + " is not in the database.\n");
                                }else if(depositWithdrawAccountType.getToggles().get(INDEX_OF_COLLEGECHECKING).isSelected()){
                                        depositWithdrawOutput.setText(newProfile.toString() + " " + "College Checking" + " is not in the database.\n");
                                }else if(depositWithdrawAccountType.getToggles().get(INDEX_0F_SAVINGS).isSelected()){
                                        depositWithdrawOutput.setText(newProfile.toString() + " " + "Savings" + " is not in the database.\n");
                                }else{
                                        depositWithdrawOutput.setText(newProfile.toString() + " " + "Money Market" + " is not in the database.\n");
                                }
                        }else{
                                String balance = validWithdraw(depositWithdrawAmount.getText());
                                if(!balance.contains(ERROR_CONTAINING_DEPOSIT_OR_WITHDRAWAL)){
                                        double deposit = Double.parseDouble(depositWithdrawAmount.getText());
                                        Account acct = createAccount(newProfile, depositWithdrawAccountType, deposit);

                                        if(database.withdraw(acct)){
                                                depositWithdrawOutput.setText("Withdraw - balance updated.\n");
                                        }else{
                                                depositWithdrawOutput.setText("Withdraw - insufficient fund.\n");
                                        }
                                }else{
                                        depositWithdrawOutput.setText(validWithdraw(depositWithdrawAmount.getText()));
                                }
                        }
                }

        }


        @FXML
        void depositAmount(ActionEvent event) {

                String dbDate = depositWithdrawDob.getValue().toString();
                Date newDate = new Date(dbDate);
                if(!newDate.isValid()){
                        openCloseOutput.setText("Date of birth invalid.");
                }else{
                        Profile newProfile = new Profile(depositWithdrawFirstName.getText(), depositWithdrawLastName.getText(), newDate);
                        Account account = createAccount(newProfile, depositWithdrawAccountType,0);
                        if(!database.findAcct(account)){
                                if (depositWithdrawAccountType.getToggles().get(INDEX_0F_CHECKING).isSelected()){
                                        depositWithdrawOutput.setText(newProfile.toString() + " " + "Checking" + " is not in the database.\n");
                                }else if(depositWithdrawAccountType.getToggles().get(INDEX_OF_COLLEGECHECKING).isSelected()){
                                        depositWithdrawOutput.setText(newProfile.toString() + " " + "College Checking" + " is not in the database.\n");
                                }else if(depositWithdrawAccountType.getToggles().get(INDEX_0F_SAVINGS).isSelected()){
                                        depositWithdrawOutput.setText(newProfile.toString() + " " + "Savings" + " is not in the database.\n");
                                }else{
                                        depositWithdrawOutput.setText(newProfile.toString() + " " + "Money Market" + " is not in the database.\n");
                                }
                        }else{
                                boolean initialDeposit = false;
                                String balance = validDeposit(depositWithdrawAmount.getText(), initialDeposit);
                                if(!balance.equals("Deposit - amount cannot be 0 or negative.\n") &&
                                        !balance.equals("Not a valid amount.\n")){
                                        double deposit = Double.parseDouble(depositWithdrawAmount.getText());
                                        Account acct = createAccount(newProfile, depositWithdrawAccountType, deposit);
                                        database.deposit(acct);
                                        depositWithdrawOutput.setText("Deposit - balance updated.\n");
                                }else{
                                        depositWithdrawOutput.setText(validDeposit(depositWithdrawAmount.getText(), initialDeposit));
                                }
                        }
                }
        }

        @FXML
        void initialDepositAmount(ActionEvent event) {
                String dbDate = openCloseDob.getValue().toString();
                Date newDate = new Date(dbDate);
                Profile newProfile = new Profile(openClosefirstName.getText(), openCloseLastName.getText(), newDate);
                Account account = createAccount(newProfile, openCloseAccountType,0);
                if(!database.findAcct(account)){
                        if (openCloseAccountType.getToggles().get(INDEX_0F_CHECKING).isSelected()){
                                openCloseOutput.setText(newProfile.toString() + " " + "Checking" + " is not in the database.\n");
                        }else if(openCloseAccountType.getToggles().get(INDEX_OF_COLLEGECHECKING).isSelected()){
                                openCloseOutput.setText(newProfile.toString() + " " + "College Checking" + " is not in the database.\n");
                        }else if(openCloseAccountType.getToggles().get(INDEX_0F_SAVINGS).isSelected()){
                                openCloseOutput.setText(newProfile.toString() + " " + "Savings" + " is not in the database.\n");
                        }else{
                                openCloseOutput.setText(newProfile.toString() + " " + "Money Market" + " is not in the database.\n");
                        }
                }else{
                        boolean initialDeposit = false;
                        String balance = validDeposit(openCloseInitialAccountAmount.getText(), initialDeposit);
                        if(!balance.equals("Deposit - amount cannot be 0 or negative.\n") &&
                                !balance.equals("Not a valid amount.\n")){
                                double deposit = Double.parseDouble(openCloseInitialAccountAmount.getText());
                                Account acct = createAccount(newProfile, openCloseAccountType, deposit);
                                database.deposit(acct);
                                openCloseOutput.setText("Deposit - balance updated.\n");
                        }else{
                                openCloseOutput.setText(validDeposit(openCloseInitialAccountAmount.getText(), initialDeposit));
                        }
                }
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
                        openCloseOutput.setText("Account reopened.\n");
                }else{
                        checking.closed = false;
                        openCloseOutput.setText(profile.toString() + " same account(type) is in the database.\n");
                }
        }

        /**
         * Attempts to open an account unless a duplicate is found
         *
         *
         * @param checking Account object to be opened in array
         * @param profile Profile used to print error message if already open
         * @param database AccountDatabase to access the array of accounts
         */
        private void attemptOpen(Account checking, Profile profile, AccountDatabase database){
                StringBuilder sb = new StringBuilder();
                try{
                        if(database.duplicateAccount(checking)){
                                openCloseOutput.setText(profile.toString()+ " same account(type) is in the database.\n");
                                return;
                        }
                }catch(Exception e){}
                database.open(checking);
                openCloseOutput.setText("Account opened.\n");
        }

        /**
         * Private method for creating a Checking Account object
         * @param profile Commandline input containing first name, last name, date of birth, and initial deposit amount
         * @param depositit Database containing Account objects
         */
        private void executeCommandCaseC(Profile profile, String depositit){

                boolean initialDeposit = true;
                String balance = validDeposit(openCloseInitialAccountAmount.getText(), initialDeposit);
                if(balance.contains(ERROR_CONTAINING_DEPOSIT_OR_WITHDRAWAL)){
                        openCloseOutput.setText(balance);
                }else{
                        double deposit = Double.parseDouble(validDeposit(depositit, initialDeposit));
                        Checking checking = new Checking(profile, deposit);
                        if(database.findAcct(checking)){
                                attemptReopen(checking, profile, database);
                        }else{
                                attemptOpen(checking, profile, database);
                        }
                }
        }




        private void executeCommandCaseCC(Profile profile, String depositit, int campusCode){

                boolean initialDeposit = true;
                String balance = validDeposit(openCloseInitialAccountAmount.getText(), initialDeposit);
                if(balance.contains(ERROR_CONTAINING_DEPOSIT_OR_WITHDRAWAL)){
                        openCloseOutput.setText(balance);
                }else{
                        double deposit = Double.parseDouble(validDeposit(depositit, initialDeposit));
                        CollegeChecking checking = new CollegeChecking(profile, deposit, campusCode);
                        if(database.findAcct(checking)){
                                attemptReopen(checking, profile, database);
                        }else{
                                attemptOpen(checking, profile, database);
                        }
                }
        }

        private void executeCommandCaseS(Profile profile, String depositit, int loyaltyCode){

                boolean initialDeposit = true;
                String balance = validDeposit(openCloseInitialAccountAmount.getText(), initialDeposit);
                if(balance.contains(ERROR_CONTAINING_DEPOSIT_OR_WITHDRAWAL)){
                        openCloseOutput.setText(balance);
                }else{
                        double deposit = Double.parseDouble(validDeposit(depositit, initialDeposit));
                        Savings savings = new Savings(profile, deposit, loyaltyCode);
                        boolean attempt = false;
                        if(savings.closed){
                                attempt = true;
                        }
                        if(!database.open(savings)){
                                openCloseOutput.setText(profile.toString()+ " same account(type) is in the database.\n");
                                return;
                        }
                        if(database.findAcct(savings) && attempt){
                                openCloseOutput.setText("Account reopened.\n");
                        }else{
                                openCloseOutput.setText("Account opened.\n");
                        }
                }
        }

        private void executeCommandCaseMM(Profile profile, String depositit){
                /*
                Date date = new Date(dob);
                if(!date.isValid()){
                        System.out.println("Date of birth invalid.\n");
                        return;
                }
                 */
                boolean initialDeposit = true;
                String balance = validDeposit(openCloseInitialAccountAmount.getText(), initialDeposit);
                if(balance.contains(ERROR_CONTAINING_DEPOSIT_OR_WITHDRAWAL)){
                        openCloseOutput.setText(balance);
                }else{
                        double deposit = Double.parseDouble(validDeposit(depositit, initialDeposit));
                        MoneyMarket checking = new MoneyMarket(profile, deposit);

                        if(deposit<2500){
                                openCloseOutput.setText("Minimum of $2500 to open a MoneyMarket account.\n");
                                return;
                        }

                        if(database.findAcct(checking) && database.getAccount(checking).getType().equals(checking.getType())){
                                attemptReopen(checking, profile, database);
                        }else{
                                attemptOpen(checking, profile, database);
                        }
                }

        }

        @FXML
        void openCloseAccount(ActionEvent event) {
                String dbDate = openCloseDob.getValue().toString();
                Date newDate = new Date(dbDate);
                if(!newDate.isValid()){
                        openCloseOutput.setText("Date of birth invalid.");
                }else{
                        Profile newProfile = new Profile(openClosefirstName.getText(), openCloseLastName.getText(), newDate);
                        String deposit = openCloseInitialAccountAmount.getText();
                        if (openAccount.isSelected()){

                                if (openCloseChecking.isSelected()){

                                        executeCommandCaseC(newProfile, deposit);

                                }else if (openCloseCollegeChecking.isSelected()){

                                        int campusCode = 0;
                                        if (openCloseCamden.isSelected()){
                                                campusCode = INDEX_OF_CAMDEN;
                                        }else if (openCloseNewark.isSelected()){
                                                campusCode = INDEX_OF_NEWARK;
                                        }else if (openCloseNB.isSelected()){
                                                campusCode = INDEX_OF_NB;
                                        }
                                        executeCommandCaseCC(newProfile, deposit, campusCode);

                                }else if (openCloseSavings.isSelected()){
                                        int loyalty = NON_LOYAL;;

                                        if (openCloseLoyalCustomer.isSelected()){
                                                loyalty = LOYAL;
                                        }
                                        executeCommandCaseS(newProfile, deposit, loyalty);

                                }else if (openCloseMoneyMarket.isSelected()){

                                        openCloseLoyalCustomer.setSelected(true);

                                        executeCommandCaseMM(newProfile, deposit);

                                }

                        }else if (closeAccount.isSelected()){
                        /*
                        String dbDate = openCloseDob.getValue().toString();
                                Date newDate = new Date(dbDate);
                                Profile newProfile = new Profile(openClosefirstName.getText(), openCloseLastName.getText(), newDate);
                                String deposit = openCloseInitialAccountAmount.getText();
                         */


                                try {
                                        //delete
                                        //System.out.println(openCloseAccountType);
                                        Account acct = createAccount(newProfile, openCloseAccountType, 0);

                                        if(database.findCertain(acct) != NOT_FOUND){
                                                int index = database.findCertain(acct);

                                                if(database.alreadyClosed(index)){
                                                        database.close(acct);
                                                        openCloseOutput.setText("Account is closed already.\n");
                                                }else{
                                                        database.close(acct);
                                                        openCloseOutput.setText("Account closed.\n");
                                                }
                                        }


                                } catch (Exception e) {
                                        openCloseOutput.setText("Missing data for closing an account.\n");
                                }
                        } else {
                                openCloseOutput.setText("Missing data for closing an account.........\n");
                        }
                }


        }



        @FXML
        void openAccountClicked(MouseEvent event) {

                openCloseInitialAccountAmount.setDisable(false);

        }

        @FXML
        void closeAccountClicked(MouseEvent event) {

                openCloseInitialAccountAmount.setDisable(true);

        }

        @FXML
        void openCloseCheckingClicked(MouseEvent event) {

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
        void openCloseCollegeCheckingClicked(MouseEvent event) {

                openCloseLoyalCustomer.setSelected(false);

                openCloseCamden.setDisable(false);
                openCloseNewark.setDisable(false);
                openCloseNB.setDisable(false);

                openCloseLoyalCustomer.setDisable(true);

        }

        @FXML
        void openCloseMoneyMarketClicked(MouseEvent event) {

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
        void openCloseSavingsClicked(MouseEvent event) {

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
