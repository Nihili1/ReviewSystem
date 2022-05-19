package com.zy.service;

import com.zy.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestService {

    @Resource
    public TestMapper testMapper;

    public void batchImport(){
        for (int i = 0; i <5 ; i++) {

            testMapper.insert();
        }
    }

}
