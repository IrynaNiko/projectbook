package org.iryna.projectbook.service;

import org.iryna.projectbook.pojo.CompanyFilesSection;
import org.iryna.projectbook.pojo.FilePB;

import java.util.List;

public interface CompanyFilesService {

    CompanyFilesSection        createSection(CompanyFilesSection Csection);

    List<CompanyFilesSection>  readAllSections();

    CompanyFilesSection        readSectionById(int id);

    void                       saveFileBySection(int id, FilePB newFile);

    CompanyFilesSection        updateSection(CompanyFilesSection section);

    Integer                    deleteSection(int id);
}
