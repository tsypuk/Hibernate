package model;

import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by rtsy on 19.12.2015.
 */
@Entity
@Table(name = "reports")
@Proxy(lazy = true)
public class Report implements Serializable{
    @Id
    @Column(name = "ID")
    @GeneratedValue (strategy = GenerationType.AUTO)
    Long _id;
    @Column(name = "name")
    String _name;
    @Column(name = "date")
    Date _date;

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public Date getDate() {
        return _date;
    }

    public void setDate(Date date) {
        _date = date;
    }

//    This will drop the store in Set
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//
//        Report report = (Report) o;
//
//        if (_id != null ? !_id.equals(report._id) : report._id != null) {
//            return false;
//        }
//
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        return _id != null ? _id.hashCode() : 0;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Report report = (Report) o;

        if (_date != null ? !_date.equals(report._date) : report._date != null) {
            return false;
        }
        if (_name != null ? !_name.equals(report._name) : report._name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = _name != null ? _name.hashCode() : 0;
        result = 31 * result + (_date != null ? _date.hashCode() : 0);
        return result;
    }
}
