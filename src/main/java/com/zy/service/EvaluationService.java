package com.zy.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.entity.Book;
import com.zy.entity.Evaluation;

import java.util.List;

public interface EvaluationService {

    /**
     * 按图书编号查询有效短评
     * @param bookId
     * @return 评论列表
     */

    public List<Evaluation> selectByBookId(Long bookId);

    public Evaluation selectByEvaluationId(Long evaluationId);

    public void updateDisable(Evaluation evaluation);

    public IPage<Evaluation> pagination(Integer page, Integer rows);

}
