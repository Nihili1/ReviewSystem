package com.zy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zy.entity.Category;
import com.zy.mapper.CategoryMapper;
import com.zy.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("categoryService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    /**
     *  查询所有图书分类
     */

    @Resource
    private CategoryMapper categoryMapper;


    public List<Category> selectAll() {

        List<Category> list = categoryMapper.selectList(new QueryWrapper<Category>());

        return list;


    }






}
