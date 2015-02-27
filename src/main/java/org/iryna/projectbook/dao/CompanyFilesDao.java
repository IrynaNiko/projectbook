package org.iryna.projectbook.dao;

import org.iryna.projectbook.pojo.CompanyFilesSection;
import org.iryna.projectbook.pojo.FilePB;

import java.util.List;

public interface CompanyFilesDao {

    CompanyFilesSection createSection(CompanyFilesSection section);

    List<CompanyFilesSection>  readAllSections();

    CompanyFilesSection        readSectionById(int id);

    void                       saveFileBySection(int id, FilePB newFile);

    CompanyFilesSection        updateSection(CompanyFilesSection section);

    Integer                    deleteSection(int id);

}
