package com.project.crewz;

import com.project.crewz.member.MemberService;
import com.project.crewz.member.UserPool;
import com.project.crewz.moim.Moim;
import com.project.crewz.moim.MoimService;
import com.project.crewz.category.Category;
import com.project.crewz.category.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

@Controller
@RequestMapping("/")
public class Index {
    private CategoryService categoryService;
    private MoimService moimService;
    private MemberService memberService;
    private UserPool userPool;

    @Value("${spring.servlet.multipart.location}")
    private String path;

    /**
     * 첫 화면
     * @param m
     * @param request
     * @return
     */
    @RequestMapping
    public String home(Model m, HttpServletRequest request) {
        m.addAttribute("catlist", categories());

        return "index";
    }

    /**
     * 카테고리 클릭시 화면
     * @param no
     * @param m
     * @return
     */
    @RequestMapping("/search")
    public String moimlist(int no, Model m) {
        ArrayList<Moim> list = moimService.findByCategory(no);
        m.addAttribute("catlist", categories());
        m.addAttribute("list", list);

        // 카테고리 검색
        for(Category category : categories()) {
            if(category.getNo() == no) {
                m.addAttribute("num", no);
                m.addAttribute("category", category.getName());
            }
        }

        return "search";
    }

    /**
     * 검색시 화면
     * @param no
     * @param word
     * @param m
     * @return
     */
    @RequestMapping("/search/{no}/msg")
    public String moimList(@PathVariable("no") int no, String word, Model m) {
        if(no == 1000) m.addAttribute("list", moimtitle(word));
        else if(no == 2000) m.addAttribute("list", moimid(word));
        else if(no != 0 && (word.equals(null) || word == null)) m.addAttribute("list", moimCategory(no));
        else m.addAttribute("list", moimCategoryAndTitle(no, word));
        m.addAttribute("catlist", categories());

        // 카테고리 검색
        for(Category category : categories()) {
            if(category.getNo() == no) {
                m.addAttribute("num", no);
                m.addAttribute("category", category.getName());
                return "search";
            }
        }

        // 그 외 검색
        if(no == 1000) {
            m.addAttribute("num", no);
            m.addAttribute("category", "모임제목");
        }
        else {
            m.addAttribute("num", no);
            m.addAttribute("category", "작성자");
        }

        return "search";
    }

    /**
     * 카테고리 이미지
     * @param filename
     * @return
     */
    @ResponseBody
    @RequestMapping("/category")
    public ResponseEntity<byte[]> getImg(String filename) {
        File f = new File(path + "category" + File.separator + filename);

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

    /**
     * 모임 이미지
     * @param name
     * @param no
     * @param filename
     * @return
     */
    @ResponseBody
    @RequestMapping("/search/category")
    public ResponseEntity<byte[]> getImg(String name, int no, String filename) {
        File f = new File(path + "moim" + File.separator + "member" + File.separator + name + File.separator + no + File.separator + filename);

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

    /* 메서드 */
    public ArrayList<Category> categories() {
        ArrayList<Category> list = categoryService.getAll();

        return list;
    }

    public ArrayList<Moim> moimCategory(int no) {
        ArrayList<Moim> list = moimService.findByCategory(no);

        return list;
    }

    public ArrayList<Moim> moimCategoryAndTitle(int no, String title) {
        ArrayList<Moim> list = moimService.findByCategoryAndTitle(no, title);

        return list;
    }

    public ArrayList<Moim> moimtitle(String title) {
        ArrayList<Moim> list = moimService.findByTitle(title);

        return list;
    }

    public ArrayList<Moim> moimid(String memberid) {
        ArrayList<Moim> list = moimService.findByMemberid(memberid);

        return list;
    }

    /* Autowired */
    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setMoimService(MoimService moimService) {
        this.moimService = moimService;
    }

    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Autowired
    public void setUserPool(UserPool userPool) {
        this.userPool = userPool;
    }
}
