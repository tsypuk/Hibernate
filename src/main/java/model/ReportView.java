package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by rtsy on 17.01.2016.
 */
@Entity
@Table(name = "reportsview")
public class ReportView {
    @Id
    @Column (name = "ID")
    Long id;
    @Column (name = "name")
    String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
