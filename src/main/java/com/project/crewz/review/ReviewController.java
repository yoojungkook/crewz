package com.project.crewz.review;

import java.io.File;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/test")
public class ReviewController {
    @Autowired
    private ReviewService service;

    @Autowired
    private ReviewDao dao;

    @Value("${spring.servlet.multipart.location}")
    private String fileDir;

    @GetMapping("/review")
    public void allReview(Model model) {
        ArrayList<Review> list = service.getAll();
        model.addAttribute("list",list);
    }

    @PostMapping("/add")
    public String upload(@ModelAttribute("r") Review r) throws Exception {

        int no = dao.getNextNo() +1;
        r.setNo(no);

        MultipartFile[] photos = {r.getPhoto1(), r.getPhoto2(), r.getPhoto3()};

        String[] fileNames = new String[3];

        File storeDir = new File(fileDir + (no));
        if (!storeDir.exists()) {
            storeDir.mkdirs();
        }

        for (int i = 0; i < photos.length; i++) {
            MultipartFile file = photos[i];
            if (!file.isEmpty()) {
                String originalFileName = file.getOriginalFilename();
                fileNames[i] = originalFileName;
                file.transferTo(new File(storeDir, fileNames[i]));
            }
        }

        r.setFilename1(fileNames[0]);
        r.setFilename2(fileNames[1]);
        r.setFilename3(fileNames[2]);

        service.addReview(r);

        return "redirect:/test/review";
    }
    @RequestMapping("/del")
    public String del(@RequestParam("no") int no) {
        service.delReview(no);
        return "redirect:/test/review";
    }

    @GetMapping("/edit")
    @ResponseBody
    public ResponseEntity<Review> getReview(@RequestParam("no") int no) {

        Review r = service.getReview(no);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("r") Review r, @RequestParam("photo1") MultipartFile photo1,
                                                      @RequestParam("photo2") MultipartFile photo2,
                                                      @RequestParam("photo3") MultipartFile photo3) throws Exception {

        Review original = service.getReview(r.getNo());

        MultipartFile[] photos = {photo1, photo2, photo3};
        String[] fileNames = {original.getFilename1(), original.getFilename2(), original.getFilename3()};
        File storeDir = new File(fileDir + r.getNo());
        if (!storeDir.exists()) {
            storeDir.mkdirs();
        }
        for (int i = 0; i < photos.length; i++) {
            MultipartFile file = photos[i];
            if (!file.isEmpty()) {
                String originalFileName = file.getOriginalFilename();
                fileNames[i] = originalFileName;
                file.transferTo(new File(storeDir, fileNames[i]));
            }
        }
        r.setFilename1(fileNames[0]);
        r.setFilename2(fileNames[1]);
        r.setFilename3(fileNames[2]);
        service.editReview(r);
        return "redirect:/test/review";

    }

//    @GetMapping("/reviewtest-form")
//    public void addForm() {
//    }

}

