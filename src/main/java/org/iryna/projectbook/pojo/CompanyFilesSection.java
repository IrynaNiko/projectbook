package org.iryna.projectbook.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "company_info")
public class CompanyFilesSection implements Serializable {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "name")
    private String 	name;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(name="company_files",
            joinColumns={@JoinColumn(name="section_id", referencedColumnName="`id`")},
            inverseJoinColumns={@JoinColumn(name="file_id", referencedColumnName="`id`")})
    private List<FilePB> fileList;

    public CompanyFilesSection(){ }

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

    public List<FilePB> getFileList() {
        return fileList;
    }

    public void setFileList(List<FilePB> fileList) {
        this.fileList = fileList;
    }
}
