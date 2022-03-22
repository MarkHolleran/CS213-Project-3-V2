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


        @FXML
        void depositAmount(ActionEvent event) {

        }




        @FXML
        void openCloseAccount(ActionEvent event) {

                if (openAccount.isSelected() == true){



                        String dbDate = openCloseDob.getValue().toString();

                        Date newDate = new Date(dbDate);





                        Profile newProfile = new Profile(openClosefirstName.getText(), openCloseLastName.getText(), newDate);

                        double initAccountAmount = Double.parseDouble(openCloseInitialAccountAmount.getText());



                        if (openCloseChecking.isSelected() == true){

                                Checking newChecking = new Checking(newProfile, initAccountAmount);

                                database.open(newChecking);


                        }else if (openCloseCollegeChecking.isSelected() == true){


                                if (openCloseCamden.isSelected() == true){

                                        CollegeChecking newCollegeChecking = new CollegeChecking(newProfile, initAccountAmount, INDEX_OF_CAMDEN);
                                        database.open(newCollegeChecking);



                                }else if (openCloseNewark.isSelected() == true){

                                        CollegeChecking newCollegeChecking = new CollegeChecking(newProfile, initAccountAmount, INDEX_OF_NEWARK);
                                        database.open(newCollegeChecking);


                                }else if (openCloseNB.isSelected() == true){

                                        CollegeChecking newCollegeChecking = new CollegeChecking(newProfile, initAccountAmount, INDEX_OF_NB);
                                        database.open(newCollegeChecking);


                                }else{

                                }

                        }else if (openCloseSavings.isSelected() == true){

                                if (openCloseLoyalCustomer.isSelected() == false){

                                        Savings newSavings = new Savings(newProfile, initAccountAmount,NON_LOYAL);
                                        database.open(newSavings);

                                }else{
                                        Savings newSavings = new Savings(newProfile, initAccountAmount,LOYAL);
                                        database.open(newSavings);

                                }

                        }else if (openCloseMoneyMarket.isSelected() == true){

                                openCloseLoyalCustomer.setSelected(true);

                                if (openCloseLoyalCustomer.isSelected() == false){

                                        MoneyMarket newMoneyMarket = new MoneyMarket(newProfile, initAccountAmount);

                                        newMoneyMarket.loyalCustomer = NON_LOYAL;

                                        database.open(newMoneyMarket);


                                }else{

                                        MoneyMarket newMoneyMarket = new MoneyMarket(newProfile, initAccountAmount);

                                        database.open(newMoneyMarket);

                                }

                        }




                }

                if (closeAccount.isSelected() == true){



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


        @FXML
        void withdrawAmount(ActionEvent event) {

        }

}
