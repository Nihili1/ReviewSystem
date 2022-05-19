package com.zy.service;

import com.zy.entity.PlusTest;
import com.zy.mapper.TestPlusMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestPlusServiceTest {

    @Resource
    private TestPlusMapper testPlusMapper;


    @Test
    public void insert1() {

        PlusTest plusTest =new PlusTest();

        plusTest.setContent("plus测试 1");

        testPlusMapper.insert(plusTest);

        System.out.println("success");

    }

    @Test
    public void testUpdate(){

        PlusTest plusTest = testPlusMapper.selectById(1);

        plusTest.setContent("update测试");

        testPlusMapper.updateById(plusTest);

    }

    @Test
    public void testDelet(){
         testPlusMapper.deleteById(2);



    }


}