package org.iryna.projectbook.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "task")
public class Task implements Serializable {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "name")
    private String 		name;

    @Column(name = "description")
    private String 		description;

    @Column(name = "date_creation")
    @Type(type="date")
    private Date dateCreation;

    @Column(name = "date_started")
    @Type(type="date")
    private Date dateStarted;

    @Column(name = "date_deadline")
    @Type(type="date")
    private Date deadline;

    @Column(name = "date_finished")
    @Type(type="date")
    private Date dateFinished;

    @ManyToOne(fetch=FetchType.EAGER, targetEntity = Status.class)
    private Status status;

    @ManyToOne(fetch=FetchType.EAGER, targetEntity = Priority.class)
    private Priority priority;

    @ManyToOne(fetch=FetchType.EAGER, targetEntity = User.class)
    private User manager;

    @ManyToOne(fetch=FetchType.EAGER, targetEntity = User.class)
    private User executive;

    @ManyToOne(fetch=FetchType.EAGER, targetEntity = Project.class)
    private Project project;

    public Task		(){
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public Date getDateStarted() {
        return dateStarted;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Date getDateFinished() {
        return dateFinished;
    }

    public Status getStatus() {
        return status;
    }

    public Priority getPriority() {
        return priority;
    }

    public User getManager() {
        return manager;
    }

    public User getExecutive() {   return executive;   }

    public Project getProject() {
        return project;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setDateStarted(Date dateStarted) {
        this.dateStarted = dateStarted;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setDateFinished(Date dateFinished) {
        this.dateFinished = dateFinished;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public void setExecutive(User executive) {
        this.executive = executive;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
