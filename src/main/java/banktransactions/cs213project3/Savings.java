package banktransactions.cs213project3;

/**
 * Class that represents a Savings Account
 *
 * A savings account object consists of a Profile object,
 * an integer of type double for the account balance, and
 *an integer representing a loyal or non loyal account
 *
 * @author Mark Holleran, Abhitej Bokka
 */
public class Savings extends Account {

    public static final double NO_FEE = 0;
    public static final double MONTHLY_SAVINGS_FEE = 6;
    public static final double NONINTEREST_RATE_PERCENTAGE = 0.3/100;
    public static final double LOYALINTEREST_RATE_PERCENTAGE = 0.45/100;
    public static final double MONTHS_IN_A_YEAR = 12;
    public static final double BALANCE_IF_WAIVED = 300;
    public static final String ACCOUNT_TYPE = "Savings";
    public int loyalCustomer;

    /**
     * Creates a Savings Account object
     *
     * Profile object contains a Profile object, balance, and an integer
     * signifying a loyal account.
     *
     * @param profile Profile object that holds first name, last name, and date of birth
     * @param balance Double value representing account balance
     * @param loyalCustomer Integer value of 1 if a loyal customer, 0 otherwise
     */
    public Savings(Profile profile, double balance, int loyalCustomer ) {

        super.holder = profile;
        super.closed = false;
        super.deposit(balance);
        this.loyalCustomer = loyalCustomer;

    }

    /**
     *
     * @param obj Instance of Account to compare against another Account object
     * @return True if Account Profile and Type are same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Savings && !(obj instanceof MoneyMarket)) {
            Savings acct = (Savings) obj;
            return this.holder.equals(acct.holder);
        }
        return false;
    }

    /**
     *Returns Savings account object as a String
     *
     * @return String representation of Savings account object
     */
    @Override
    public String toString(){
        if (loyalCustomer == 1 && !super.toString().contains("CLOSED")) {

            return super.toString() + "::Loyal";

        }else

            return super.toString();

    }

    /**
     *Returns current balance plus monthly interest
     *
     *if non loyal customer return balance + .3% of balance
     *if loyal customer return balance + .45% of balance
     * @return Double representing balance with added interest
     */
    public double monthlyInterest(){

        //doesn't protect against out of bounds ints

        if (loyalCustomer == 0) {

            return this.balance * NONINTEREST_RATE_PERCENTAGE/MONTHS_IN_A_YEAR;

        }else {

            return this.balance * LOYALINTEREST_RATE_PERCENTAGE/MONTHS_IN_A_YEAR;

        }

    }

    /**
     * Returns fee if balance is below threshold
     *
     * If balance is less than $300 a fee of 6 dollars is applied
     * otherwise, no fee
     *
     * @return Double representing account fee
     */
    public double fee(){

        if(this.balance>=BALANCE_IF_WAIVED){

            return NO_FEE;

        }else{

            return MONTHLY_SAVINGS_FEE;

        }

    }

    /**
     * Decreases balance in account by specified amount
     *
     * Calls withdraw method in superclass to
     * subtract amount from balance within Account
     *
     * @param amount Amount to be withdrawn from account balance
     */
    public void withdraw(double amount){

        super.withdraw(amount);

    }

    /**
     * Increases balance in account by specified amount
     *
     * Calls deposit method in superclass to
     * add amount to current balance
     *
     * @param amount Amount to be added to account balance
     */
    public void deposit(double amount){

        super.deposit(amount);

    }

    /**
     * Returns the specified name of the Account
     *
     * @return String representing Account type
     */
    public String getType(){

        return ACCOUNT_TYPE;

    }

}
