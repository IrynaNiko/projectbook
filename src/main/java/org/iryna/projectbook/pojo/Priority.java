package org.iryna.projectbook.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "priority")
public class Priority implements Serializable {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "name")
    private String name;

    public Priority() {}

    public Priority(String name) {this.name = name;}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

}

