package com.zy.service;

import com.zy.entity.Evaluation;
import com.zy.entity.Member;
import com.zy.entity.MemberReadState;


public interface MemberService {
    /**
     *  会员注册
     *
     */

    public Member createMember(String username,String password,String nickname);


    /**
     * 检查登录
     * @param username 用户名
     * @param password 密码
     * @return 登录对象
     */
    public Member checkLogin(String username,String password);


    /**
     * 获取阅读状态
     * @param memberId 会员编号
     * @param bookId   图书编号
     * @return
     */
    public MemberReadState selectMemberReadState(Long memberId,Long bookId);

    public Member selectById(Long memberId);


    public MemberReadState updateMemberReadState(Long memberId,Long bookId,Integer readState);

    public Evaluation evaluate(Long memberId,Long bookId,Integer score,String content);

    public Evaluation enjoy(Long evaluationId);
}
