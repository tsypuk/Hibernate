package model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * Created by rtsy on 26.12.2015.
 */

@NamedQueries ({
        @NamedQuery (
                name = "findUniqueNameComments",
                query = "SELECT DISTINCT c._name from Comment c"
        ),
        @NamedQuery (
                name = "getCommentsCount",
                query = "SELECT COUNT(*) from Comment c"
        ),
        @NamedQuery (
                name = "getCommentsNameLike",
                query = "from Comment c WHERE c._name LIKE :value"
        )
})
@Entity
@Table (name = "comments")
@DynamicUpdate
@DynamicInsert
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
        region = "commentCacheRegion")
public class Comment implements Serializable{
    @Version
    @Column(name = "OPTLOCK")
    int version;
    @Id
    @Column (name = "ID")
    @GeneratedValue (strategy = GenerationType.AUTO)
    Long _id;
    @Column(name = "name")
    String _name;
    @Column(name = "text")
    String _text;

    public String getText() {
        return _text;
    }

    public void setText(String text) {
        _text = text;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        _id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "version=" + version +
                ", _id=" + _id +
                ", _name='" + _name + '\'' +
                '}';
    }
}