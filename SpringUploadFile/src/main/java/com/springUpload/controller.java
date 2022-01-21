package com.springUpload;

import com.springUpload.config.FilePath;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.nio.channels.MulticastChannel;
import java.util.Map;

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

        filePath.getPath()

        String pass = request.getSession().getServletContext().getRealPath("/");

        return null;
    }

}
