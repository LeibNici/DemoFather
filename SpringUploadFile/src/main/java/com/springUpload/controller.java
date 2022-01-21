package com.springUpload;

import com.springUpload.config.FilePath;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author chenming
 * @description controller
 * @create: 2022-01-20
 */
@RestController
public class controller {

    @Autowired
    FilePath filePath;

    @PostMapping("/upload")
    public String sd(@Param("file") MultipartFile file, HttpServletRequest request) throws IOException {

        String pass = request.getSession().getServletContext().getRealPath("/");

        return null;
    }

}
