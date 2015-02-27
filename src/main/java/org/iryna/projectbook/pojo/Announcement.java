package org.iryna.projectbook.pojo;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "announcement")
public class Announcement implements Serializable {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "name")
    private String 	name;

    @Column(name = "description")
    private String 	description;

    @Column(name = "date_creation")
    @Type(type="timestamp")
    private Date dateCreation;

    @ManyToOne(fetch=FetchType.EAGER, targetEntity = User.class)
    private User 	user;

    @OneToOne(fetch=FetchType.EAGER, targetEntity = FilePB.class)
    private FilePB 	file;

    public Announcement (){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FilePB getFile() {
        return file;
    }

    public void setFile(FilePB file) {
        this.file = file;
    }
}
