package com.zy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.entity.Book;
import com.zy.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BookServiceImplTest {

    @Resource
    private BookService bookService;

    @Test
    public void pagination() {
        IPage<Book> pageObject = bookService.pagination(2l,"quantity",1, 10);

        List<Book> records = pageObject.getRecords();

        for (Book b:records){
            System.out.println(b.getBookId()+":"+b.getBookName());
        }

        System.out.println(pageObject.getPages());
        System.out.println(pageObject.getTotal());


    }
}