package com.zy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zy.entity.Evaluation;
import com.zy.entity.Member;
import com.zy.entity.MemberReadState;
import com.zy.mapper.EvaluationMapper;
import com.zy.mapper.MemberReadStateMapper;
import com.zy.utils.BussinessException;
import com.zy.mapper.MemberMapper;
import com.zy.service.MemberService;
import com.zy.utils.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Service("memberService")
@Transactional
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private MemberReadStateMapper memberReadStateMapper;


    @Resource
    private EvaluationMapper evaluationMapper;


    public Member createMember(String username, String password, String nickname) {

        QueryWrapper<Member> queryWrapper=new QueryWrapper<Member>();

        queryWrapper.eq("username",username);

        List<Member> memberList = memberMapper.selectList(queryWrapper);

        if (memberList.size()>0){
            throw new BussinessException("eM01","用户名已存在");
        }

       int salt=new Random().nextInt(1000)+1000;

        String md5 = MD5Utils.md5Digest(password, salt);

        Member member=new Member();
        member.setUsername(username);
        member.setPassword(md5);
        member.setNickname(nickname);
        member.setSalt(salt);
        member.setCreateTime(new Date());

        memberMapper.insert(member);


        return member;
    }


    public Member checkLogin(String username, String password) {

        QueryWrapper<Member> queryWrapper=new QueryWrapper<Member>();

        queryWrapper.eq("username",username);

        Member member = memberMapper.selectOne(queryWrapper);

        if(member==null){
            throw new BussinessException("eM02","用户不存在");
        }

        String md5 = MD5Utils.md5Digest(password, member.getSalt());

        if(!md5.equals(member.getPassword())){
            throw new BussinessException("eM03","密码错误");
        }

        return member;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public MemberReadState selectMemberReadState(Long memberId, Long bookId) {
        QueryWrapper<MemberReadState> queryWrapper=new QueryWrapper<MemberReadState>();

        queryWrapper.eq("member_id",memberId);
        queryWrapper.eq("book_id",bookId);

        MemberReadState memberReadState = memberReadStateMapper.selectOne(queryWrapper);


        return memberReadState;
    }

    public Member selectById(Long memberId) {

        QueryWrapper<Member> queryWrapper=new QueryWrapper<Member>();
        queryWrapper.eq("member_id",memberId);

        Member member = memberMapper.selectOne(queryWrapper);
        return member;
    }

    public MemberReadState updateMemberReadState(Long memberId, Long bookId, Integer readState) {

        QueryWrapper<MemberReadState> queryWrapper=new QueryWrapper<MemberReadState>();

        queryWrapper.eq("book_id",bookId);
        queryWrapper.eq("member_id",memberId);

        MemberReadState memberReadState = memberReadStateMapper.selectOne(queryWrapper);

        if(memberReadState==null){
            memberReadState = new MemberReadState();

            memberReadState.setBookId(bookId);
            memberReadState.setMemberId(memberId);
            memberReadState.setCreateTime(new Date());
            memberReadState.setReadState(readState);
            memberReadStateMapper.insert(memberReadState);

        }else {
              memberReadState.setReadState(readState);
              memberReadStateMapper.updateById(memberReadState);
        }

        return memberReadState;
    }

    public Evaluation evaluate(Long memberId, Long bookId, Integer score, String content) {
        Evaluation evaluation=new Evaluation();

        evaluation.setMemberId(memberId);
        evaluation.setBookId(bookId);
        evaluation.setScore(score);
        evaluation.setContent(content);
        evaluation.setCreateTime(new Date());
        evaluation.setState("enable");
        evaluation.setEnjoy(0);
        evaluationMapper.insert(evaluation);

        return evaluation;
    }

    public Evaluation enjoy(Long evaluationId) {

        Evaluation evaluation = evaluationMapper.selectById(evaluationId);

        evaluation.setEnjoy(evaluation.getEnjoy()+1);

        evaluationMapper.updateById(evaluation);
        return  evaluation;
    }

}
