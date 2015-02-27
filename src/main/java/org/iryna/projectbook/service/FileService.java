package org.iryna.projectbook.service;

import org.iryna.projectbook.pojo.FilePB;
import java.util.List;

public interface FileService {
    
    FilePB        createFile(FilePB file);

    List<FilePB>  readAllFiles();

    FilePB        getFileById(int id);

    FilePB        updateFile(FilePB file);

    FilePB        deleteFile(int id);
}
