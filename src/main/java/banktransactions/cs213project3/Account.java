package banktransactions.cs213project3;

import java.text.DecimalFormat;

/**
 * Class that represents an abstract Account Object
 *
 * An Account object contains a Profile object,
 * a boolean representing a closed or open account,
 * and a double representing the current balance.
 *
 * @author Mark Holleran, Abhitej Bokka
 */
public abstract class Account {

    protected Profile holder;
    protected boolean closed;
    protected double balance;

    /**
     * Constructor for Creating an Account object
     */
    public Account (){

    }

    /**
     *Compares two Account objects for equality
     *
     * @param obj Instance of Account object to compare against another Account object
     * @return True if Account Profile and Type are same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Account) {

            Account acct = (Account) obj;

            return this.holder.equals(acct.holder);
        }
        return false;
    }

    /**
     * Returns a String representation of an Account object
     *
     * @return String containing Account type, holder information, balance, and status
     */
    @Override
    public String toString() {

        DecimalFormat dformat = new DecimalFormat("#,##0.00");

        StringBuilder sb = new StringBuilder(getType() + "::" + holder.toString() + "::Balance $" + dformat.format(balance));

        if (closed) {
            sb.append("::CLOSED");
        }
        return sb.toString();
    }

    /**
     * Decreases balance in account by specified amount
     *
     * amount is subtracted from the balance with an Account object
     *
     * @param amount Amount to be withdrawn from an account's balance
     */
    public void withdraw(double amount) {
        balance -= amount;
    }


    /**
     * Increases balance in an Account by a specified amount
     *
     *amount is added to the balance of an Account object
     *
     * @param amount Amount to be added to an account's balance
     */
    public void deposit(double amount) {
        balance += amount;
    }

    /**
     * Returns Profile object from an Account object
     *
     * @return Profile object containing first name, last name, and dob
     */
    public Profile getProfile(){
        return holder;
    }

    /**
     * Abstract method for returning calculated MonthlyInterest
     *
     * Each type of account has their own interest amount
     * the account's balance is returned with the added monthly interest
     * @return Account's new balance after a month has passed
     */
    public abstract double monthlyInterest();

    /**
     * Abstract method for returning calculated Account fees
     *
     * Each type of account has their own fees
     * based on how many withdraws or balance amount
     *
     * @return Fee for holding an Account
     */
    public abstract double fee();

    /**
     * Abstract method for returning the Account's type
     *
     * @return Account type as a String
     */
    public abstract String getType();

}
