package model;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rtsy on 16.01.2016.
 */
@Entity
@Table (name = "item")

public class Item {
    @Id
    @Column(name = "ID")
    @GeneratedValue (strategy = GenerationType.AUTO)
    Long _id;
    @Column(name = "name")
    String _name;

    @Column(name = "images")
    // This field will be stored in Another Table
    @ElementCollection (targetClass=String.class)
    private Set<String> images = new HashSet<String>();

    public Set getImages() {
        return images;
    }

    public void setImages(Set images) {
        this.images = images;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        _id = id;
    }
}
