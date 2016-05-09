package model.inheritance;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by rtsy on 12.12.2015.
 */
@Entity
@AttributeOverride(name = "owner", column =
        @Column(name = "CC_OWNER", nullable = false)
)
public class CreditCard extends BillingDetails {
    @Id @GeneratedValue
    @Column(name = "CREDIT_CARD_ID")
    private Long ID;

    @Column(name = "NUMBER", nullable = false)
    private String number;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
