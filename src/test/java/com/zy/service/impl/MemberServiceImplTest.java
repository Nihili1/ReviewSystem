package com.zy.service.impl;

import com.zy.entity.Member;
import com.zy.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MemberServiceImplTest {

    @Resource
    private MemberService memberService;

    @Test
    public void createMember() {

        memberService.createMember("s22222222","123456","test2");



    }
}