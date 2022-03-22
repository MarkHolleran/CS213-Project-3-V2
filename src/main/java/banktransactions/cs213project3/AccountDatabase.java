package banktransactions.cs213project3;

import java.text.DecimalFormat;

/**
 * Class that represents an Array of Account objects
 *
 * Included in this class are methods for finding an account,
 * getting the number of accounts, opening and closing an account,
 * deposit and withdrawing from an account, printing accounts
 * in various orders, and a few methods for account comparison.
 *
 * @author Mark Holleran, Abhitej Bokka
 */
public class AccountDatabase {

    public static final int NOT_FOUND = -1;
    private Account[] accounts;
    private int numAcct;

    int[] cheese = new int[23];

    /**
     * Default constructor that constructs an Array of type Account objects
     * The array is initialized to a size of 4 and the number of accounts to 0
     */
    public AccountDatabase(){
        this.accounts = new Account[4];
        this.numAcct = 0;

    }

    /**
     * Returns the current number of Account objects stored in the array
     *
     * @return Integer representing current number of Account objects within the array
     */
    public int getNumAcct(){
        return this.numAcct;
    }

    /**
     * Goes through each entry in the array searching for specified Account object
     *
     * If account is found its index is found, otherwise returns not_found
     *
     * @param account Account object being searched for
     * @return Integer value of the array index position of the Account object if found, not_found otherwise
     */
    public int find(Account account) {
        for(int i = 0; i<numAcct; i++){
            if(accounts[i].equals(account) && accounts[i].getType().equals(account.getType())){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Goes through an array searching for a specified Account object
     *
     * @param acct Account object being searched for
     * @return True or false if the Account object being searched for was found or not
     */
    public boolean findAcct(Account acct){
        for(int i = 0; i < numAcct; i++){
            if(accounts[i].equals(acct) && accounts[i].getType().equals(acct.getType())){
                return true;
            }
        }
        return false;
    }

    /**
     * Goes through an array searching for a specified Account object
     *
     * @param acct Account object being searched for
     * @return Account object if account being searched for was found or not
     */
    public Account getAccount(Account acct){
        for(int i = 0; i < numAcct; i++){
            if(accounts[i].equals(acct) && accounts[i].getType().equals(acct.getType())){
                return accounts[i];
            }
        }
        return null;
    }

    /**
     * Goes to specified array index and checks if Account is closed or not
     *
     * @param index Index position of an Array
     * @return True if the Account object at specified array index is closed, false otherwise
     */
    public boolean alreadyClosed(int index){
        if(accounts[index].closed){
            return true;
        }
        return false;
    }

    /**
     * Helper method used for resizing an Array
     *
     * Once the array is full it's size increases by 4
     */
    private void grow() {
        int resizedLength = accounts.length + 4;
        Account[] resizedArray = new Account[resizedLength];
        for(int i = 0; i < numAcct; i++){
            resizedArray[i] = accounts[i];
        }
        accounts = resizedArray;
    }

    /**
     * Goes through array searching for a duplicate of a specified Account object
     *
     * @param account Account object being searched for
     *
     * @return True if duplicate Account objecct is found, false otherwise
     */
    public boolean duplicateAccount(Account account){

            for(int i = 0; i < numAcct; i++){
                if(accounts[i].getProfile().equals(account.getProfile())
                    && account instanceof CollegeChecking
                    && accounts[i] instanceof Checking && (!(accounts[i] instanceof CollegeChecking))){
                    return true;
                }
                if(accounts[i].getProfile().equals(account.getProfile())
                        && account instanceof Checking
                        && accounts[i] instanceof CollegeChecking
                        && (!(account instanceof CollegeChecking))){
                    return true;
                }
            }
        return false;
    }


    /**
     * Adds an Account object to the array
     *
     * If the specified account already exists
     * or if the holder tries to open an account they aren't allowed to
     * return false, Otherwise the account is added to the array
     *
     * @param account Account object to be added to array
     *
     * @return True if account is added successfully, false otherwise
     */
    public boolean open(Account account) {

        if(duplicateAccount(account)) return false;

        if(find(account) == NOT_FOUND){
            if(numAcct >= this.accounts.length) this.grow();

            accounts[numAcct] = account;
            accounts[numAcct].closed = false;
            numAcct++;
            return true;
        }

        if(findAcct(account) && !account.closed) return false;

        if(findAcct(account) && account.closed){
            if(numAcct >= this.accounts.length) this.grow();

            int index = find(account);
            accounts[index] = account;
            accounts[index].closed = false;
            return true;
        }
        return false;
    }

    /**
     * Goes through array and sets Account object to closed
     *
     * @param account Account object to be closed
     *
     * @return True if account is successfully closed, false otherwise
     */
    public boolean close(Account account) {

        if(find(account) != NOT_FOUND){
            int index = find(account);
            accounts[index].closed = true;
            if(accounts[index] instanceof MoneyMarket){
                ((MoneyMarket) accounts[index]).withdrawCount = 0;
            }
            accounts[index].balance = 0;
            return true;
        }

        return false;
    }

    /**
     * Specified amount is added to the balance within an Account object
     *
     * If the account is found add specified amount to its balance,
     * otherwise do nothing
     *
     * @param account Account object to deposit money into
     */
    public void deposit(Account account) {
        if(find(account) != NOT_FOUND){
            int index = find(account);
            accounts[index].deposit(account.balance);
        }
        return;
    }

    /**
     * Specified amount is subtracted from the balance within an Account object
     *
     * If the account is found and has enough for the specified amount
     * to be subtracted, then subtract the amount and return true. Otherwise,
     * return false if the account doesn't exist or insufficient funds
     *
     * @param account Account object to withdraw a specified amount from
     * @return True if amount is successfully withdrawn, false otherwise
     */
    public boolean withdraw(Account account) {
        if(find(account) != NOT_FOUND){
            int index = find(account);
            if(account.balance > accounts[index].balance){
                return false;
            }else{
                accounts[index].withdraw(account.balance);
                return true;
            }
        }
        return false;
    }

    /**
     * Prints each Account object within the array
     */
    public String print() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<numAcct; i++){
            sb.append(this.accounts[i].toString());
        }
        return sb.toString();
    }

    /**
     * Prints each Account object within an array in order of Account type
     */
    public String printByAccountType() {
        StringBuilder sb = new StringBuilder();
        int n = numAcct;

        for(int i = 0; i < n; ++i){
            Account key = accounts[i];
            int j = i - 1;

            while(j>=0 && accounts[j].getType().compareTo(key.getType()) > 0 ){
                accounts[j + 1] = accounts[j];
                j = j - 1;
            }
            accounts[j + 1] = key;
        }

        sb.append(print());

        sb.append("*end of list.\n");
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Calculates balance after monthly interest and fees
     */
    public void calculate(){
        for(int i = 0; i < numAcct; i++){
            if(!accounts[i].closed){
                accounts[i].balance = accounts[i].balance + accounts[i].monthlyInterest() - accounts[i].fee();
            }
        }
    }

    /**
     * Prints each Account object in the array after fees are subtracted from the balance
     */
    public String printFeeAndInterest() {

        StringBuilder outer = new StringBuilder();

        for(int i = 0; i < numAcct; i++){
            DecimalFormat dformat = new DecimalFormat("#,##0.00");
            DecimalFormat dformat2 = new DecimalFormat("#,##0.00");
            StringBuilder sb = new StringBuilder(accounts[i].toString());
            sb.append("::fee $" + dformat.format(accounts[i].fee())
                + "::monthly interest $" + dformat2.format(accounts[i].monthlyInterest()));
            outer.append(sb);
        }
        outer.append("*end of list.\n");
        outer.append("\n");
        return outer.toString();
    }

}