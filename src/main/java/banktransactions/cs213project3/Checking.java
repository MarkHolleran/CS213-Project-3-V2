package banktransactions.cs213project3;
/**
 * Class that represents a Checking Account object
 *
 * Methods within this class can create a Checking
 * Account object, return the monthly interest or fees,
 * withdraw and deposit,and return account type
 *
 * @author Mark Holleran, Abhitej Bokka
 */
public class Checking extends Account {

    public static final double MONTHLY_FEE = 25;
    public static final double INTEREST_RATE_PERCENTAGE = 0.1/100;
    public static final double MONTHS_IN_A_YEAR = 12;
    public static final double BALANCE_IF_WAIVED = 1000;
    public static final String ACCOUNT_TYPE = "Checking";

    /**
     * Constructor for Creating a Checking Account object
     * @param profile Object containing first name, last name, and date of birth
     * @param balance Specified amount to initialize the Checking account with
     */
    public Checking(Profile profile, double balance){

        super.holder = profile;

        super.closed = false;

        super.deposit(balance);

    }

    /**
     *
     * @param obj Instance of Account to compare against another Account object
     * @return True if Account Profile and Type are same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Checking && !(obj instanceof CollegeChecking)) {
            Checking acct = (Checking) obj;
            return this.holder.equals(acct.holder);
        }
        return false;
    }

    /**
     * Returns balance of a Checking account with interest added
     *
     * @return Double representing the balance of a Checking account after monthly interest is added
     */
    public double monthlyInterest(){

        return this.balance*INTEREST_RATE_PERCENTAGE/MONTHS_IN_A_YEAR;

    }

    /**
     *Returns the fee to hold a Checking account
     *
     * Fee is waived if account balance is greater than $1000
     *
     * @return Double representing the fee depending on amount within a Checking account object
     */
    public double fee(){

        if(this.balance>=BALANCE_IF_WAIVED){

            return 0;

        }else{

            return MONTHLY_FEE;

        }

    }

    /**
     * Subtracts a specified amount from the balance of a Checking account
     *
     * @param amount Amount to be withdrawn from an account's balance
     */
    public void withdraw(double amount){

        super.withdraw(amount);

    }

    /**
     * Adds a specified amount to the balance of a Checking account
     *
     * @param amount Amount to be added to an account's balance
     */
    public void deposit(double amount){

        super.deposit(amount);

    }

    /**
     * Returns the account type variable of the Checking account
     *
     * @return String representing the type of account
     */
    public String getType(){

        return ACCOUNT_TYPE;

    }

}
