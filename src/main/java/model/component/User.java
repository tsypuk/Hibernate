package model.component;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by rtsy on 12.12.2015.
 */
@Entity
@Table (name = "users", schema = "study_innodb")
public class User {
    Long id;
    String firstname;
    String lastname;
    String username;
    String password;
    String email;
    Address _address;

    public User() {
    }
    @Id
    @GeneratedValue
    @Column (name = "user_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Embedded
    public Address getAddress() {
        return _address;
    }

    public void setAddress(Address address) {
        _address = address;
    }
}
