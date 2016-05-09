package model.component;

import javax.persistence.Embeddable;

/**
 * Created by rtsy on 12.12.2015.
 */
@Embeddable
public class Address {
    String street;
    String zipCode;
    String city;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
