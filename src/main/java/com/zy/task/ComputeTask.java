package com.zy.task;

import com.zy.mapper.BookMapper;
import com.zy.service.BookService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *  自动计算

 */


@Component
public class ComputeTask {

    @Resource
    private BookService bookService;

    @Scheduled(cron="0 * * * * ?")
    public void updateEvaluation(){
        bookService.updateEvaluation();
        System.out.println("update success");
    }



}
