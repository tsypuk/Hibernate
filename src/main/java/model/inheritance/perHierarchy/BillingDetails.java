package model.inheritance.perHierarchy;


/**
 * Created by rtsy on 12.12.2015.
 */

public class BillingDetails {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String owner;

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

    @Override
    public String toString() {
        return "BillingDetails{" +
                "id=" + id +
                ", owner='" + owner + '\'' +
                ", lim='" + lim + '\'' +
                '}';
    }
}
