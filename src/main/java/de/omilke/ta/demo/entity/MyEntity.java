package de.omilke.ta.demo.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Simple Entity for demonstration only.
 *
 * persistence.xml is configured to drop and create tables.
 *
 * @author Oliver Milke
 */
@Entity
@Table(name = "Entity")
public class MyEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer Id;

    @Size(max = 30)
    private String name;

    public MyEntity() {
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
