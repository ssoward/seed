package com.ssoward.service;

import com.ssoward.model.Award;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by ssoward on 4/12/14.
 */

@Service
public class FileServiceImpl implements FileService{
//    public final static String PHOTO_PATH = "/Users/ssoward/scott/scrap/"; //TODO add properties file.
    public final static String PHOTO_PATH = "/home/ec2-user/RESOURCES/apps/seed/"; //TODO add properties file.

    @Autowired
    AwardsService awardsService;

    @Override
    public void uploadAward(HttpServletRequest request, HttpServletResponse res) throws Exception {
        Award award = new Award();
        //process only if its multipart content
        if(ServletFileUpload.isMultipartContent(request)) {
            List<FileItem> multiparts = new ServletFileUpload(
                    new DiskFileItemFactory()).parseRequest(request);

            for (FileItem item : multiparts) {
                if (!item.isFormField()) {
                    String name = new File(item.getName()).getName();
                    award.setName(name);
                    File f = new File(FileServiceImpl.PHOTO_PATH + File.separator + name);
                    item.write(f);
                    FileServiceUtil.resizeImage(f);
                }
            }

            //File uploaded successfully
            request.setAttribute("message", "File Uploaded Successfully");
            awardsService.saveAward(award);
        }else{
            request.setAttribute("message",
                    "Sorry this Servlet only handles file upload request");
        }
    }

    @Override
    public byte[] getFile(Long id) {
        Award a = awardsService.getAward(id);
        String photo = PHOTO_PATH + a.getName();
        Path path = Paths.get(photo);
        byte[] award = new byte[0];
        try {
            award = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return award;
    }

    @Override
    public void deleteFile(Long id) throws IOException {
        Award a = awardsService.getAward(id);
        String photo = PHOTO_PATH + a.getName();
        Files.delete(Paths.get(photo));
    }
}
