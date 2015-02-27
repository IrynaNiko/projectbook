package org.iryna.projectbook.controller.ajax;

import org.iryna.projectbook.pojo.CompanyFilesSection;
import org.iryna.projectbook.pojo.FilePB;
import org.iryna.projectbook.service.CompanyFilesService;
import org.iryna.projectbook.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/pb/ajax/companyfiles")
public class CompanyFilesController {

    @Autowired
    private CompanyFilesService companyFilesService;

    @Autowired
    private FileService fileService;

    private static final String fileStorageDir = "C:/_MY_FILES_/DOCS/Projects/Programming/pb_file_storage/";
    private static final int maxFileSize = 15728640;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CompanyFilesSection> createSection(@RequestBody CompanyFilesSection section) {
        CompanyFilesSection newSection = companyFilesService.createSection(section);
        return new ResponseEntity<>(newSection, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CompanyFilesSection>> readAllSections() {
        List<CompanyFilesSection> resultList = companyFilesService.readAllSections();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<CompanyFilesSection> readSectionById(@PathVariable Integer id){
        CompanyFilesSection receivedSection = companyFilesService.readSectionById(id);
        return new ResponseEntity<>(receivedSection, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list/{id}")
    public ResponseEntity<List<FilePB>> readFilesBySectionId(@PathVariable Integer id){
        CompanyFilesSection receivedSection = companyFilesService.readSectionById(id);
        List<FilePB> receivedFiles = receivedSection.getFileList();
        return new ResponseEntity<>(receivedFiles, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/list/{id}")
    public ResponseEntity<FilePB> saveFileBySection(@PathVariable Integer id, MultipartHttpServletRequest request){

        Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf = null;
        FilePB newFile = null;

        while(itr.hasNext()){

            mpf = request.getFile(itr.next());

            if(mpf.getSize() >= maxFileSize){
                return new ResponseEntity<>(HttpStatus.REQUEST_ENTITY_TOO_LARGE);
            }

            try (OutputStream os = new FileOutputStream(fileStorageDir + mpf.getOriginalFilename())){
                FileCopyUtils.copy(mpf.getInputStream(), os);

            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            FilePB file = new FilePB();
            file.setName(mpf.getOriginalFilename());
            newFile = fileService.createFile(file);
        }

        companyFilesService.saveFileBySection(id, newFile);
        return new ResponseEntity<>(newFile, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyFilesSection> updateSection(@RequestBody CompanyFilesSection section) {
        CompanyFilesSection updatedSection = companyFilesService.updateSection(section);
        return new ResponseEntity<>(updatedSection, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Integer> deleteSectionById(@PathVariable Integer id){
        CompanyFilesSection receivedSection = companyFilesService.readSectionById(id);
        List<FilePB> fileList = receivedSection.getFileList();
        for (FilePB i :  fileList){
            fileService.deleteFile(i.getId());
        }
        int deleteStatus = companyFilesService.deleteSection(id);
        return new ResponseEntity<>(deleteStatus, HttpStatus.OK);
    }
}
