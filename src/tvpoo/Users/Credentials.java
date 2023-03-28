package tvpoo.Users;

public final class Credentials {
    /**
     * Details about the user account:
     * name - name of the user registered
     * password - password of the registered user
     * accountType - standard/premium account user
     * country - country of the user in which belongs
     * balance - number of tokens must remain in equilibrium
     *                      nrTokens = balance
     */
    private String name;
    private String password;
    private String accountType;
    private String country;
    private String balance;
    /**
     * @return - obtain the nickName of the User
     */
    public String getName() {
        return name;
    }
    /**
     * @param name - change the nickName of the User
     */
    public void setName(final String name) {
        this.name = name;
    }
    /**
     * @return - obtain the password of the User
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password - change the password of the User
     */
    public void setPassword(final String password) {
        this.password = password;
    }
    /**
     * @return - obtain the account type: Premium/Standard
     */
    public String getAccountType() {
        return accountType;
    }
    /**
     * @param accountType - change the account type: Premium/Standard
     */
    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }
    /**
     * @return - obtain the country of the User
     */
    public String getCountry() {
        return country;
    }
    /**
     * @param country - change the country of the User
     */
    public void setCountry(final String country) {
        this.country = country;
    }
    /**
     * @return -obtain the equilibrium of tokens MUST BE balance = buying tokens
     */
    public String getBalance() {
        return balance;
    }
    /**
     * @param balance - modify the equilibrium of tokens MUST BE balance = buying tokens
     */
    public void setBalance(final String balance) {
        this.balance = balance;
    }
}
