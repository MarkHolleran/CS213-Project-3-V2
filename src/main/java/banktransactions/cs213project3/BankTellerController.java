package banktransactions.cs213project3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;


public class BankTellerController {

	public static final int LOYAL = 1;
	public static final int NON_LOYAL = 0;
	public static final int NOT_FOUND = -1;

	public static final int INDEX_OF_CHECKING = 0;
	public static final int INDEX_OF_COLLEGE_CHECKING = 1;

	public static final int INDEX_OF_SAVINGS = 2;
	public static final int INDEX_OF_MONEY_MARKET = 3;
	public static final double BALANCE_IF_WAIVED = 2500;

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
	private TextField openCloseFirstName;

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

	/**
	 * Private method that displays all accounts in the Database Array
	 * in current ordering
	 */
	@FXML
	protected void printAllAccounts() {
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
		accountDatabaseOutput.appendText(sb.toString());
	}

	/**
	 * Private method that displays all accounts in the Database Array
	 * based on the type of account
	 */
	@FXML
	protected void printAccountsByType() {

		StringBuilder sb = new StringBuilder();
		if(database.getNumAcct()==0){
			sb.append("Account Database is empty!\n");
		}else{
			sb.append("\n");
			sb.append("*list of accounts by account type.\n");
			sb.append(database.printByAccountType());
		}
		accountDatabaseOutput.appendText(sb.toString());
	}

	/**
	 * Private method that displays all accounts in the Database Array
	 * in current ordering with fees and monthlyInterest
	 */
	@FXML
	protected void calculateFeesAndInterest() {
		StringBuilder sb = new StringBuilder();
		if(database.getNumAcct()==0){
			sb.append("Account Database is empty!\n");
		}else{
			sb.append("\n");
			sb.append("*list of accounts with fee and monthly interest\n");
			sb.append(database.printFeeAndInterest());
		}
		accountDatabaseOutput.appendText(sb.toString());
	}

	/**
	 * Private method that recalculates balances given fees and
	 * monthlyInterst and displays all accounts in the Database Array
	 * in current ordering
	 */
	@FXML
	protected void applyFeesAndInterest() {
		StringBuilder sb = new StringBuilder();
		if(database.getNumAcct()==0){
			sb.append("Account Database is empty!\n");
		}else{
			sb.append("\n");
			sb.append("*list of accounts with updated balance\n");
			database.calculate();
			sb.append(database.print());
			sb.append("*end of list.\n");
			sb.append("\n");
		}
		accountDatabaseOutput.appendText(sb.toString());
	}

	/**
	 * Creates an account based on the account type given a profile and balance
	 *
	 * @param profile Profile object that holds first name, last name, and date of birth
	 * @param type Toggled user input to identify what type of account is being selected
	 * @param balance Double value representing account balance
	 *
	 * @return an Account of either type Checking, College Checking, Savings, or Money Market
	 */
	private Account createAccount(Profile profile, ToggleGroup type, double balance){

		if(type.getToggles().get(INDEX_OF_CHECKING).isSelected()){
			return new Checking(profile,balance);
		}
		if(type.getToggles().get(INDEX_OF_COLLEGE_CHECKING).isSelected()){
			return new CollegeChecking(profile, balance, INDEX_OF_NB);
		}
		if(type.getToggles().get(INDEX_OF_SAVINGS).isSelected()){
			return new Savings(profile, balance, NON_LOYAL);
		}
		if(type.getToggles().get(INDEX_OF_MONEY_MARKET).isSelected()){
			return new MoneyMarket(profile,balance);
		}
		return null;
	}

	/**
	 * Verifies that the deposit from user input is a positive double
	 *
	 * If the deposit is invalid, the respective error message is sent to be printed
	 *
	 * @param deposit String from user potentially containing a proper deposit
	 * @param initial boolean to signify if this is the account's initial deposit
	 *
	 * @return String value of the deposit if valid, an error message otherwise
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
				return sb.toString();
			}else{
				return deposit;
			}
		}catch(Exception e) {
			sb.append("Not a valid amount.\n");
			return sb.toString();
		}
	}

	/**
	 * Verifies that the requested withdrawal from user input is a positive double
	 *
	 * If the withdrawal is invalid, the respective error message is to be printed
	 *
	 * @param deposit String from user potentially containing a proper withdrawal
	 *
	 * @return String value of the withdrawal if valid, an error message otherwise
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

	/**
	 * Verifies that the name does not have numbers and certain special characters
	 *
	 * @param name String from user input
	 *
	 * @return boolean value indicating whether name is valid or not
	 */
	private boolean validName(String name){
		return name.matches( "[a-zA-Z]+([ '-][a-zA-Z]+)*" );
	}

	/**
	 * Private method for subtracting specified
	 * amount from the balance of an Account object
	 *
	 * If any data is improperly formatted, the respective error is given
	 */
	@FXML
	protected void withdrawAmount() {
		try{
			String dbDate = depositWithdrawDob.getValue().toString();
			Date newDate = new Date(dbDate);
			if(!newDate.isValid() || !validName(depositWithdrawFirstName.getText())
					|| !validName(depositWithdrawLastName.getText())){
				if(!newDate.isValid()){
					depositWithdrawOutput.appendText("Date of birth invalid.\n");
				}else{
					depositWithdrawOutput.appendText("Please enter a valid name.\n");
				}
			}else{
				Profile newProfile = new Profile(depositWithdrawFirstName.getText(), depositWithdrawLastName.getText(), newDate);
				Account account = createAccount(newProfile, depositWithdrawAccountType,0);
				if(!database.findAcct(account)){
					accountNotFound(depositWithdrawAccountType, depositWithdrawOutput, newProfile);
				}else{
					boolean initialDeposit = false;
					String balance = validWithdraw(depositWithdrawAmount.getText());
					boolean isDouble = true;
					double deposit = 0;
					try{
						deposit = Double.parseDouble(balance);
					}catch(Exception e){
						isDouble = false;
					}
					if(isDouble){
						Account acct = createAccount(newProfile, depositWithdrawAccountType, deposit);
						if(database.withdraw(acct)){
							depositWithdrawOutput.appendText("Withdraw - balance updated.\n");
						}else{
							depositWithdrawOutput.appendText("Withdraw - insufficient fund.\n");
						}
					}else{
						depositWithdrawOutput.appendText(validWithdraw(depositWithdrawAmount.getText()));
					}
				}
			}
		}catch(Exception e){
			depositWithdrawOutput.appendText("Missing data for withdrawing from an account.\n");
		}
	}

	/**
	 * Private method for notifying user that
	 * a non-existent user was requested.
	 */
	private void accountNotFound(ToggleGroup type, TextArea area, Profile newProfile){
		if (type.getToggles().get(INDEX_OF_CHECKING).isSelected()){
			area.appendText(newProfile.toString() + " " + "Checking" + " is not in the database.\n");
		}else if(type.getToggles().get(INDEX_OF_COLLEGE_CHECKING).isSelected()){
			area.appendText(newProfile.toString() + " " + "College Checking" + " is not in the database.\n");
		}else if(type.getToggles().get(INDEX_OF_SAVINGS).isSelected()){
			area.appendText(newProfile.toString() + " " + "Savings" + " is not in the database.\n");
		}else{
			area.appendText(newProfile.toString() + " " + "Money Market" + " is not in the database.\n");
		}
	}

	/**
	 * Private method for depositing amount into an Account object
	 *
	 * If any data is improperly formatted, the respective error is given
	 */
	@FXML
	protected void depositAmount(ActionEvent event) {
		try{
			String dbDate = depositWithdrawDob.getValue().toString();
			Date newDate = new Date(dbDate);
			if(!newDate.isValid() || !validName(depositWithdrawFirstName.getText())
					|| !validName(depositWithdrawLastName.getText())){
				if(!newDate.isValid()){
					depositWithdrawOutput.appendText("Date of birth invalid.\n");
				}else{
					depositWithdrawOutput.appendText("Please enter a valid name.\n");
				}
			}else{
				Profile newProfile = new Profile(depositWithdrawFirstName.getText(), depositWithdrawLastName.getText(), newDate);
				Account account = createAccount(newProfile, depositWithdrawAccountType,0);
				if(!database.findAcct(account)){
					accountNotFound(depositWithdrawAccountType, depositWithdrawOutput, newProfile);

				}else{
					boolean initialDeposit = false;
					String balance = validDeposit(depositWithdrawAmount.getText(), initialDeposit);
					boolean isDouble = true;
					double deposit = 0;
					try{
						deposit = Double.parseDouble(balance);
					}catch(Exception e){
						isDouble = false;
					}
					if(isDouble){
						Account acct = createAccount(newProfile, depositWithdrawAccountType, deposit);
						database.deposit(acct);
						depositWithdrawOutput.appendText("Deposit - balance updated.\n");
					}else{
						depositWithdrawOutput.appendText(validDeposit(depositWithdrawAmount.getText(), initialDeposit));
					}
				}
			}
		}catch(Exception e){
			depositWithdrawOutput.appendText("Missing data for depositing to an account.\n");
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
			openCloseOutput.appendText("Account reopened.\n");
		}else{
			checking.closed = false;
			openCloseOutput.appendText(profile.toString() + " same account(type) is in the database.\n");
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
		try{
			if(database.duplicateAccount(checking)){
				openCloseOutput.appendText(profile.toString()+ " same account(type) is in the database.\n");
				return;
			}
		}catch(Exception e){

		}
		database.open(checking);
		openCloseOutput.appendText("Account opened.\n");
	}

	/**
	 * Private method for creating a Checking Account object
	 *
	 * @param profile Input containing first name, last name, date of birth, and initial deposit amount
	 * @param depositit String from user potentially containing a proper deposit
	 */
	private void executeCommandCaseC(Profile profile, String depositit){
		boolean initialDeposit = true;
		String balance = validDeposit(depositit, initialDeposit);
		double deposit = 0;
		boolean isDouble = true;
		try{
			deposit = Double.parseDouble(balance);
		}catch(Exception e){
			isDouble = false;
		}
		if(!isDouble){
			openCloseOutput.appendText(balance);
		}else{
			Checking checking = new Checking(profile, deposit);
			if(database.findAcct(checking)){
				attemptReopen(checking, profile, database);
			}else{
				attemptOpen(checking, profile, database);
			}
		}
	}



	/**
	 * Private method for creating a College Checking Account object
	 *
	 * @param profile Input containing first name, last name, date of birth, and initial deposit amount
	 * @param depositit String from user potentially containing a proper deposit
	 * @param campusCode integer assigning the campus that is tied to the account
	 */
	private void executeCommandCaseCC(Profile profile, String depositit, int campusCode){

		boolean initialDeposit = true;
		String balance = validDeposit(depositit, initialDeposit);
		double deposit = 0;
		boolean isDouble = true;
		try{
			deposit = Double.parseDouble(balance);
		}catch(Exception e){
			isDouble = false;
		}
		if(!isDouble){
			openCloseOutput.appendText(balance);
		}else{
			CollegeChecking checking = new CollegeChecking(profile, deposit, campusCode);
			if(database.findAcct(checking)){
				attemptReopen(checking, profile, database);
			}else{
				attemptOpen(checking, profile, database);
			}

		}
	}

	/**
	 * Private method for creating a Savings Account object
	 *
	 * @param profile Input containing first name, last name, date of birth, and initial deposit amount
	 * @param depositit String from user potentially containing a proper deposit
	 * @param loyaltyCode integer notifying if the account is loyal or not
	 */
	private void executeCommandCaseS(Profile profile, String depositit, int loyaltyCode){

		boolean initialDeposit = true;
		String balance = validDeposit(depositit, initialDeposit);
		double deposit = 0;
		boolean isDouble = true;
		try{
			deposit = Double.parseDouble(balance);
		}catch(Exception e){
			isDouble = false;
		}
		if(!isDouble){
			openCloseOutput.appendText(balance);
		}else{
			Savings savings = new Savings(profile, deposit, loyaltyCode);

			if(database.findAcct(savings) && database.getAccount(savings).getType().equals(savings.getType())){
				attemptReopen(savings, profile, database);
			}else{
				attemptOpen(savings, profile, database);
			}
		}
	}

	/**
	 * Private method for creating a Money Market Account object
	 *
	 * @param profile Input containing first name, last name, date of birth, and initial deposit amount
	 * @param depositit String from user potentially containing a proper deposit
	 */
	private void executeCommandCaseMM(Profile profile, String depositit){
		boolean initialDeposit = true;
		String balance = validDeposit(depositit, initialDeposit);
		double deposit = 0;
		boolean isDouble = true;
		try{
			deposit = Double.parseDouble(balance);
		}catch(Exception e){
			isDouble = false;
		}
		if(!isDouble){
			openCloseOutput.appendText(balance);
		}else{
			MoneyMarket checking = new MoneyMarket(profile, deposit);
			if(deposit<BALANCE_IF_WAIVED){
				openCloseOutput.appendText("Minimum of $2500 to open a MoneyMarket account.\n");
				return;
			}
			if(database.findAcct(checking) && database.getAccount(checking).getType().equals(checking.getType())){
				attemptReopen(checking, profile, database);
			}else{
				attemptOpen(checking, profile, database);
			}
		}
	}

	/**
	 * Private method that attempts to Open or Close an Account
	 *
	 * If the command follows the proper formatting,
	 * the command will move forward to execute
	 * commands of different account type.
	 */
	@FXML
	protected void openCloseAccount() {
		try{
			String dbDate = openCloseDob.getValue().toString();
			Date newDate = new Date(dbDate);
			if(!newDate.isValid() || !validName(openCloseFirstName.getText())
					|| !validName(openCloseLastName.getText())){
				if(!newDate.isValid()){
					openCloseOutput.appendText("Date of birth invalid.\n");
				}else{
					openCloseOutput.appendText("Please enter a valid name.\n");
				}
			}else{
				Profile newProfile = new Profile(openCloseFirstName.getText(), openCloseLastName.getText(), newDate);
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

					try {
						Account acct = createAccount(newProfile, openCloseAccountType, 0);

						if(database.findCertain(acct) != NOT_FOUND){
							int index = database.findCertain(acct);

							if(database.alreadyClosed(acct)){
								database.close(acct);
								openCloseOutput.appendText("Account is closed already.\n");
							}else{
								database.close(acct);
								openCloseOutput.appendText("Account closed.\n");
							}
						}else{
							accountNotFound(openCloseAccountType, openCloseOutput, newProfile);
						}

					} catch (Exception e) {
						openCloseOutput.appendText("Missing data for closing an account.\n");
					}
				} else {
					openCloseOutput.appendText("Select open or close.\n");
				}
			}
		}catch(Exception e){
			openCloseOutput.appendText("Missing data for opening/closing an account.\n");
		}
	}



	/**
	 * Method that allows users to only select either open or close
	 *
	 * Method to help keep buttons disabled to reduce potential errors from user input
	 */
	@FXML
	protected void openAccountClicked() {
		openCloseInitialAccountAmount.setDisable(false);
	}

	/**
	 * Method that allows users to only select either open or close
	 *
	 * Method to help keep buttons disabled to reduce potential errors from user input
	 */
	@FXML
	protected void closeAccountClicked() {
		openCloseInitialAccountAmount.setDisable(true);

	}

	/**
	 * Method that allows users to select only Checking Account
	 *
	 * Helps disable buttons like loyalty or campus that are unnecessary
	 */
	@FXML
	protected void openCloseCheckingClicked() {
		openCloseChecking.setSelected(true);

		openCloseLoyalCustomer.setSelected(false);

		openCloseNB.setSelected(false);
		openCloseCamden.setSelected(false);
		openCloseNewark.setSelected(false);

		openCloseCamden.setDisable(true);
		openCloseNewark.setDisable(true);
		openCloseNB.setDisable(true);

		openCloseLoyalCustomer.setDisable(true);

	}

	/**
	 * Method that allows users to select only College Checking Account
	 *
	 * Helps disable buttons like loyalty that are unnecessary
	 */
	@FXML
	protected void openCloseCollegeCheckingClicked() {

		openCloseCollegeChecking.setSelected(true);
		openCloseLoyalCustomer.setSelected(false);

		openCloseNB.setSelected(true);

		openCloseCamden.setDisable(false);
		openCloseNewark.setDisable(false);
		openCloseNB.setDisable(false);

		openCloseLoyalCustomer.setDisable(true);

	}

	/**
	 * Method that allows users to select only Money Market Account
	 *
	 * Helps disable buttons like campus that are unnecessary and keeps loyalty selected
	 */
	@FXML
	protected void openCloseMoneyMarketClicked() {

		openCloseMoneyMarket.setSelected(true);
		openCloseNB.setSelected(false);
		openCloseCamden.setSelected(false);
		openCloseNewark.setSelected(false);

		openCloseCamden.setDisable(true);
		openCloseNewark.setDisable(true);
		openCloseNB.setDisable(true);
		openCloseLoyalCustomer.setSelected(true);
		openCloseLoyalCustomer.setDisable(true);

	}

	/**
	 * Method that allows users to select only Savings Account
	 *
	 * Helps disable buttons like campus that are unnecessary and allows us to use loyalty
	 */
	@FXML
	protected void openCloseSavingsClicked() {
		openCloseSavings.setSelected(true);
		openCloseLoyalCustomer.setSelected(false);

		openCloseNB.setSelected(false);
		openCloseCamden.setSelected(false);
		openCloseNewark.setSelected(false);

		openCloseCamden.setDisable(true);
		openCloseNewark.setDisable(true);
		openCloseNB.setDisable(true);
		openCloseLoyalCustomer.setDisable(false);

	}
}