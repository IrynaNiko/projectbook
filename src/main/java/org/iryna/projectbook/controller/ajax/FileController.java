package org.iryna.projectbook.controller.ajax;

import org.apache.commons.io.FileUtils;
import org.iryna.projectbook.pojo.FilePB;
import org.iryna.projectbook.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import org.springframework.util.FileCopyUtils;
import java.util.List;

@Controller
@RequestMapping("/pb/ajax/file")

public class FileController {

    @Autowired
    private FileService fileService;

    private static final String fileStorageDir = "C:/_MY_FILES_/DOCS/Projects/Programming/pb_file_storage/";
    private static final int maxFileSize = 15728640;

    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<FilePB> upload(MultipartHttpServletRequest request) {

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
        return new ResponseEntity<>(newFile, HttpStatus.OK);
    }

    @RequestMapping(value = "/file_storage/{id}", method = RequestMethod.GET)
    public void getRawFileById(HttpServletResponse response, @PathVariable Integer id){
        FilePB receivedFilePB = fileService.getFileById(id);

        response.setContentType(new MimetypesFileTypeMap().getContentType(receivedFilePB.getName()));
        response.setHeader("Content-disposition", "attachment; filename=\""+receivedFilePB.getName()+"\"");

        try (InputStream is = new FileInputStream(fileStorageDir + receivedFilePB.getName())){
            FileCopyUtils.copy(is, response.getOutputStream());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<FilePB>> readAllFiles() {
        List<FilePB> resultList = fileService.readAllFiles();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FilePB> updateFile(@RequestBody FilePB FilePB) {
        FilePB updatedFilePB = fileService.updateFile(FilePB);
        return new ResponseEntity<>(updatedFilePB, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Integer> deleteFileById(@PathVariable Integer id){

        FilePB receivedFilePB = fileService.deleteFile(id);
        FileUtils.deleteQuietly(new File(fileStorageDir + receivedFilePB.getName()));

        return new ResponseEntity<>(receivedFilePB.getId(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> deleteMultipleFiles(@RequestBody Integer filesArrId[]){
        for (Integer i :  filesArrId){
            fileService.deleteFile(i);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

