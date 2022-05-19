package com.zy.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.entity.Book;
import com.zy.service.BookService;
import com.zy.utils.BussinessException;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management/book")
public class MBookController {
    @Resource
    private BookService bookService;

    @GetMapping("/index.html")
    public ModelAndView showBook() {
        return new ModelAndView("/management/book");
    }


    @PostMapping("/upload")
    @ResponseBody
    public Map upload(@RequestParam("img") MultipartFile file, HttpServletRequest request) throws Exception {

        //获取上传目录
        String uploadPath = request.getServletContext().getResource("/").getPath() + "/upload/";

        //获取文件名
        String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

        //获取扩展名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        //另存为
        file.transferTo(new File(uploadPath + fileName + suffix));

        Map result = new HashMap();

        result.put("errno", 0);
        result.put("data", new String[]{"/upload/" + fileName + suffix});
        return result;
    }


    @PostMapping("/create")
    @ResponseBody
    public Map createBook(Book book) {

        Map result = new HashMap();

        try {
            book.setEvaluationQuantity(0);
            book.setEvaluationScore(0f);

            Document doc = Jsoup.parse(book.getDescription());

            Element img = doc.select("img").first();
            String srcValue = img.attr("src");

            book.setCover(srcValue);

            bookService.createBook(book);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException e) {
            e.printStackTrace();
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
        }

        return result;

    }

    @GetMapping("/list")
    @ResponseBody
    public Map list(Integer page, Integer limit) {
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }

        IPage<Book> pageObject = bookService.pagination(null, null, page, limit);
        Map result = new HashMap();
        result.put("code", "0");
        result.put("msg", "success");
        result.put("data", pageObject.getRecords());
        result.put("count", pageObject.getTotal());

        //以上result返回数据，layUi必须要求

        return result;

    }

    @GetMapping("/id/{id}")
    @ResponseBody
    public Map selectById(@PathVariable("id") Long bookId) {
        Book book = bookService.selectById(bookId);

        Map result = new HashMap();

        result.put("code", "0");
        result.put("msg", "success");
        result.put("data", book);

        return result;
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateBook(Book book) {

        Map result = new HashMap();

        try {
            Book originBook = bookService.selectById(book.getBookId());

            originBook.setBookName(book.getBookName());
            originBook.setSubTitle(book.getSubTitle());
            originBook.setAuthor(book.getAuthor());
            originBook.setCategoryId(book.getCategoryId());
            originBook.setDescription(book.getDescription());

            Document doc = Jsoup.parse(book.getDescription());

            String cover = doc.select("img").first().attr("src");

            originBook.setCover(cover);

            bookService.updateBook(book);
            result.put("code", "0");
            result.put("msg", "success");

        } catch (BussinessException e) {
            e.printStackTrace();
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
        }
        return result;
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public Map deleteBook(@PathVariable("id") Long bookId) {
        Map result = new HashMap();
        try {
            bookService.deleteBook(bookId);
            result.put("code", "0");
            result.put("msg", "success");

        } catch (BussinessException e) {
            e.printStackTrace();
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
        }
        return result;

    }


}
