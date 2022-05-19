package com.zy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.entity.Book;
import com.zy.entity.Member;
import com.zy.mapper.BookMapper;
import com.zy.mapper.EvaluationMapper;
import com.zy.mapper.MemberMapper;
import com.zy.service.EvaluationService;
import com.zy.entity.Evaluation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service("evaluationService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class EvaluationServiceImpl implements EvaluationService {

    @Resource
    private EvaluationMapper evaluationMapper;

    @Resource
    private BookMapper bookMapper;

    @Resource
    private MemberMapper memberMapper;

    public List<Evaluation> selectByBookId(Long bookId) {

        Book book = bookMapper.selectById(bookId);

        QueryWrapper<Evaluation> queryWrapper=new QueryWrapper<Evaluation>();

        queryWrapper.eq("book_id",bookId);

        queryWrapper.eq("state","enable");

        queryWrapper.orderByDesc("create_time");

        List<Evaluation> evaluations = evaluationMapper.selectList(queryWrapper);

        for (Evaluation eva: evaluations) {

            Member member = memberMapper.selectById(eva.getMemberId());

            eva.setMember(member);

            eva.setBook(book);
        }

        return evaluations;



    }

    public Evaluation selectByEvaluationId(Long evaluationId) {
        Evaluation evaluation = evaluationMapper.selectById(evaluationId);
        return evaluation;
    }

    public void updateDisable(Evaluation evaluation) {
        evaluationMapper.updateById(evaluation);
    }


    public IPage<Evaluation> pagination(Integer page, Integer rows) {

          Page<Evaluation> p=new Page(page,rows);

        QueryWrapper<Evaluation> evaluationQueryWrapper = new QueryWrapper<Evaluation>();

        IPage<Evaluation> objPage = evaluationMapper.selectPage(p, evaluationQueryWrapper);
        objPage.getRecords();

        return objPage;
    }



}
