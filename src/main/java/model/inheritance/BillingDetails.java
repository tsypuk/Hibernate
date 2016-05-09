package model.inheritance;


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by rtsy on 12.12.2015.
 */
@MappedSuperclass
public abstract class BillingDetails {
    @Column(name = "OWNER", nullable = false)
    private String owner;
    @Column(name = "LIM", nullable = false)
    private String lim;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLim() {
        return lim;
    }

    public void setLim(String lim) {
        this.lim = lim;
    }

    public BillingDetails() {
    }
}
