package banktransactions.cs213project3;

/**
 * Class that represents a MoneyMarket account
 *
 * Methods within this class can create a Money Market Account,
 *  calculate balance after monthly interest, return fees,
 *  withdraw and deposit, get account type, and return
 *  a String representation of a Money Market account object
 *
 * @author Mark Holleran, Abhitej Bokka
 */
public class MoneyMarket extends Savings {

    public final double NO_FEE = 0;
    public final double MONTHLY_FEE = 10;
    public final double MM_ADDITIONAL_INTEREST_RATE_PERCENTAGE = 0.8/100;
    public final double MM_LOYAL_INTEREST_RATE_PERCENTAGE = 0.15/100;
    public static final double MONTHS_IN_A_YEAR = 12;
    public static final int DEFAULT_LOYALTY = 1;
    public int withdrawCount = 0;
    public static final int MAX_WITHDRAW_LIMIT = 3;
    public static final int loyalDeleteString = 7;

    public final double BALANCE_IF_WAIVED = 2500;
    public static final String ACCOUNT_TYPE = "Money Market Savings";
    public int loyalCustomer = 1;

    /**
     *Constructor for creating a Money Market Account object
     *
     * @param profile Object containing first name, last name, and date of birth
     * @param balance Specified amount to initialize the Checking account with
     */
    public MoneyMarket(Profile profile, double balance){

        super(profile, balance, DEFAULT_LOYALTY);

        if(balance >= BALANCE_IF_WAIVED){

            loyalCustomer = 1;
        }else{
            loyalCustomer = 0;
        }
        super.closed = false;
    }

    /**
     * Compares two Money Market Account objects for equality
     *
     * @param obj Instance of Account to compare against another Account object
     * @return True if both are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MoneyMarket) {
            MoneyMarket acct = (MoneyMarket) obj;
            return this.holder.equals(acct.holder);
        }
        return false;
    }

    /**
     * Returns balance of a Money Market account with interest added
     *
     * @return Double representing the balance of a Money Market account after monthly interest is added
     */
    @Override
    public double monthlyInterest() {

        double newTotal = this.balance * MM_ADDITIONAL_INTEREST_RATE_PERCENTAGE/MONTHS_IN_A_YEAR;

        if(loyalCustomer == 1){
            newTotal += this.balance * MM_LOYAL_INTEREST_RATE_PERCENTAGE/MONTHS_IN_A_YEAR;
        }

        if(newTotal >= BALANCE_IF_WAIVED){
            loyalCustomer = 1;
        }
        return newTotal;
    }

    /**
     * subtracts a specified amount from the balance of a Money Market Account
     *
     * @param amount Amount to be withdrawn from account balance
     */
    @Override
    public void withdraw(double amount){

        super.withdraw(amount);
        withdrawCount++;

        if (this.balance < BALANCE_IF_WAIVED){

            loyalCustomer = 0;
        }
    }

    /**
     * Adds a specified amount to the balance of a Money Market account
     *
     * @param amount Amount to be added to account balance
     */
    public void deposit (double amount){
        super.deposit(amount);
    }

    /**
     * Returns the fee to hold a Money Market account
     *
     * Fee is waived if account balance is greater than $2500 and withdraws does not exceed 3
     *
     * @return Double representing the fee depending on amount within a Money Market object
     */
    @Override
    public double fee(){

        if (withdrawCount < MAX_WITHDRAW_LIMIT && balance >= BALANCE_IF_WAIVED){

            return NO_FEE;
        }else {
            return MONTHLY_FEE;
        }
    }

    /**
     * Returns the account type variable of the Checking account
     *
     * @return String representing the type of account
     */
    public String getType(){
        return ACCOUNT_TYPE;
    }

    /**
     *Returns Money Market Account object as a String
     *
     * @return String representation of a Money Market object
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(super.toString());

        if(super.toString().contains("::Loyal") && loyalCustomer == 0){
            sb.delete(sb.length()-loyalDeleteString,sb.length());
        }else{
            return super.toString() + "::withdrawl: " + withdrawCount;
        }
        sb.append("::withdrawl: " + withdrawCount);
        return sb.toString();
    }

}
