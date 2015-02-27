package org.iryna.projectbook.service.impl;

import org.iryna.projectbook.dao.CompanyFilesDao;
import org.iryna.projectbook.pojo.CompanyFilesSection;
import org.iryna.projectbook.pojo.FilePB;
import org.iryna.projectbook.service.CompanyFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyFilesServiceImpl implements CompanyFilesService {

    @Autowired
    private CompanyFilesDao companyFilesDao;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public CompanyFilesSection createSection(CompanyFilesSection section) {
        return companyFilesDao.createSection(section);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<CompanyFilesSection> readAllSections() {
        return companyFilesDao.readAllSections();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public CompanyFilesSection readSectionById(int id) {
        return companyFilesDao.readSectionById(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void saveFileBySection(int id, FilePB newFile) {
        companyFilesDao.saveFileBySection(id, newFile);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public CompanyFilesSection updateSection(CompanyFilesSection section) {
        return companyFilesDao.updateSection(section);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Integer deleteSection(int id) {
        return companyFilesDao.deleteSection(id);
    }
}
