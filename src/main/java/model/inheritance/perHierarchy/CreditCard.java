package model.inheritance.perHierarchy;

/**
 * Created by rtsy on 12.12.2015.
 */

public class CreditCard extends BillingDetails {

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
