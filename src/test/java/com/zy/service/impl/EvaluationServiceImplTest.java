package com.zy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.entity.Book;
import com.zy.entity.Evaluation;
import com.zy.service.EvaluationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class EvaluationServiceImplTest {

    @Resource
    private EvaluationService evaluationService;


    @Test
    public void pagination() {
        IPage<Evaluation> objPage = evaluationService.pagination(1, 10);

        List<Evaluation> records = objPage.getRecords();

        for (Evaluation eva: records
             ) {

            System.out.println(eva.getContent());

        }
    }
}