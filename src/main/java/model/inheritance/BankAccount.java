package model.inheritance;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by rtsy on 12.12.2015.
 */
@Entity
@AttributeOverrides({
@AttributeOverride (name = "owner", column =
@Column (name = "BA_OWNER", nullable = false)),
})
public class BankAccount extends BillingDetails{
    @Id
    @GeneratedValue
    @Column(name = "BANK_ACCOUNT_ID")
    private Long ID;
    @Column(name = "ACCOUNT", nullable = false)
    private String account;
    @Column(name = "BANK", nullable = false)
    private String bankName;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

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
