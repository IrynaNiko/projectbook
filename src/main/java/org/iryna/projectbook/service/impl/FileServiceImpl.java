package org.iryna.projectbook.service.impl;

import org.iryna.projectbook.dao.FileDao;
import org.iryna.projectbook.pojo.FilePB;
import org.iryna.projectbook.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public FilePB createFile(FilePB file) {
        return fileDao.createFile(file);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<FilePB> readAllFiles() {
        return fileDao.readAllFiles();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public FilePB getFileById(int id) {
        return fileDao.getFileById(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public FilePB updateFile(FilePB file) {
        return fileDao.updateFile(file);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public FilePB deleteFile(int id) {
        return fileDao.deleteFile(id);
    }
}
