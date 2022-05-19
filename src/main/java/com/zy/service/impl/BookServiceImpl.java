package com.zy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.entity.Book;
import com.zy.entity.Evaluation;
import com.zy.entity.MemberReadState;
import com.zy.mapper.BookMapper;
import com.zy.mapper.EvaluationMapper;
import com.zy.mapper.MemberReadStateMapper;
import com.zy.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("bookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class BookServiceImpl implements BookService {

    @Resource
    private BookMapper bookMapper;

    @Resource
    private MemberReadStateMapper memberReadStateMapper;

    @Resource
    private EvaluationMapper evaluationMapper;


    public IPage<Book> pagination(Long categoryId, String order, Integer page, Integer rows) {

        Page<Book> p = new Page(page, rows);

        QueryWrapper<Book> queryWrapper = new QueryWrapper<Book>();

        if (categoryId != null && categoryId != -1) {
            queryWrapper.eq("category_id", categoryId);
        }

        if (order != null) {
            if (order.equals("quantity")) {
                queryWrapper.orderByDesc("evaluation_quantity");
            }else if (order.equals("score")){
                queryWrapper.orderByDesc("evaluation_score");
            }

        }


        IPage<Book> pageObject = bookMapper.selectPage(p, queryWrapper);  // 第一个参数：page对象，第二个参数：条件构造器


        return pageObject;
    }

    public Book selectById(Long bookId) {
        Book book = bookMapper.selectById(bookId);

        return book;
    }

    @Transactional
    public void updateEvaluation() {
         bookMapper.updateEvaluation();
    }


    @Transactional
    public Book createBook(Book book) {
        bookMapper.insert(book);
        return book;
    }

    @Transactional
    public Book updateBook(Book book) {

        bookMapper.updateById(book);
        return book;
    }



    @Transactional
    public void deleteBook(Long bookId) {

        bookMapper.deleteById(bookId);

        QueryWrapper<MemberReadState> mrsQueryWrapper = new QueryWrapper<MemberReadState>();

        mrsQueryWrapper.eq("book_id",bookId);

        memberReadStateMapper.delete(mrsQueryWrapper);

        QueryWrapper<Evaluation>  evaluationQueryWrapper=new QueryWrapper<Evaluation>();

        evaluationQueryWrapper.eq("book_id",bookId);

        evaluationMapper.delete(evaluationQueryWrapper);

    }


}
