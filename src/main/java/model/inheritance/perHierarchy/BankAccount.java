package model.inheritance.perHierarchy;

/**
 * Created by rtsy on 12.12.2015.
 */

public class BankAccount extends BillingDetails {

    private String account;

    private String bankName;



    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
