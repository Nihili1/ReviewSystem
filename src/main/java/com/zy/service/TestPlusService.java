package com.zy.service;


import com.zy.mapper.TestPlusMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestPlusService {

    @Resource
    private TestPlusMapper testPlusMapper;


    public void insert1(){

        for (int i = 0; i <6 ; i++) {
            testPlusMapper.insertSample();

        }
    }


}
