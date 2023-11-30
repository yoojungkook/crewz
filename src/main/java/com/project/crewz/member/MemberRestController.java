package com.project.crewz.member;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@CrossOrigin("*")
public class MemberRestController {
    @Value("${spring.servlet.multipart.location}")
    private String path;

    @RequestMapping("/member/img/{id}")
    public ResponseEntity<byte[]> getImg(@PathVariable("id") String id) {
        File f = new File(path + "member" + File.separator + id);
        if(!f.isDirectory()) {
            f = new File(path + "member" + File.separator + "default.png");
        }

        HttpHeaders header = new HttpHeaders();
        ResponseEntity<byte[]> result = null;
        try {
            header.add("Content-Type", Files.probeContentType(f.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(f), header, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
