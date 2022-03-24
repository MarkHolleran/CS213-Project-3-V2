package banktransactions.cs213project3;

/**
 * Class that represents a College Checking Account object
 *
 * A College Checking Account object contains a Profile object,
 * balance, and a campus code. Within this class there are
 * methods to deposit, withdraw, return monthly interest,
 * get type, and toString methods.
 *
 * @author Mark Holleran, Abhitej Bokka
 */
public class CollegeChecking extends Checking {

    public final double MONTHLY_FEE = 0;
    public final double ADDITONAL_INTEREST_RATE_PERCENTAGE = .15/100;
    public static final double MONTHS_IN_A_YEAR = 12;
    public final int CAMPUS_ZERO = 0;
    public final int CAMPUS_ONE = 1;
    public final String ACCOUNT_TYPE = "College Checking";
    private int campusCode;


    /**
     * Constructor for a College Checking Account object
     *
     * @param profile Profile object containing first name, last name, and date of birth
     * @param balance Double representing amount to be initially deposited into the Account
     * @param campusCode Representing which campus
     */
    public CollegeChecking(Profile profile, double balance, int campusCode){
        super(profile, balance);
        this.campusCode = campusCode;
    }

    /**
     *
     * @param obj Instance of Account to compare against another Account object
     * @return True if Account Profile and Type are same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CollegeChecking) {
            CollegeChecking acct = (CollegeChecking) obj;
            return this.holder.equals(acct.holder);
        }
        return false;
    }

    /**
     *
     * @return Balance of an Account object with the monthly interest added to the balance
     */
    public double monthlyInterest(){
        return super.monthlyInterest() + this.balance * ADDITONAL_INTEREST_RATE_PERCENTAGE/MONTHS_IN_A_YEAR;
    }

    /**
     * Adds a specified amount to the balance of a College Checking Account
     *
     * @param amount Amount to add to the balance of a College Checking Account object
     */
    public void deposit(double amount){
       super.deposit(amount);
    }

    /**
     * Subtracts a specified amount from the balance of an Account
     *
     * @param amount Amount to subtract from the balance of an Account object
     */
    public void withdraw(double amount){
        super.withdraw(amount);
    }

    /**
     * Returns the account type variable of an Account
     *
     * @return String containing the type of Account
     */
    public String getType(){
        return ACCOUNT_TYPE;
    }

    /**
     * Returns the fee to hold a College Checking Account
     *
     * @return Double representing the fee to hold a College Checking Account
     */
    public double fee() {
        return MONTHLY_FEE;
    }

    /**
     * Returns the Account type, profile object, balance,
     * and campus as a String
     *
     * @return String representing a College Checking Account object
     */
    @Override
    public String toString(){

        if (campusCode == CAMPUS_ZERO) {
            return super.toString() + "::" + "NEW_BRUNSWICK";
        }else if (campusCode == CAMPUS_ONE){
            return super.toString() + "::" + "NEWARK";
        }else
            return super.toString() + "::" + "CAMDEN";
    }
}
