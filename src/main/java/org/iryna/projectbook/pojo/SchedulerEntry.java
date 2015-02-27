package org.iryna.projectbook.pojo;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "scheduler")
public class SchedulerEntry {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id", unique = true)
    private int id;

    @ManyToOne(fetch=FetchType.EAGER, targetEntity = User.class)
    private User user;

    @Column(name = "name")
    private String 	title;

    @Column(name = "start_time")
    @Type(type="timestamp")
    private Date start;

    @Column(name = "end_time")
    @Type(type="timestamp")
    private Date end;

    public SchedulerEntry(){
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
