package com.zy.controller;

import com.zy.entity.Evaluation;
import com.zy.entity.Member;
import com.zy.service.MemberService;
import com.zy.utils.BussinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {

    @Resource
    private MemberService memberService;

    @GetMapping("/register.html")
    public ModelAndView showRegister(){
        return new ModelAndView("/register");
    }

    @PostMapping("/registe")
    @ResponseBody
    public Map registe(String vc, String username, String password, String nickname, HttpServletRequest request){

        Map result=new HashMap();

        //获取图片显示验证码
        String verifyCode =(String) request.getSession().getAttribute("kaptchaVerifyCode");

        //与输入验证码比对
        if(vc==null || verifyCode==null || !vc.equalsIgnoreCase(verifyCode)){
              result.put("Ecode","VcError1");
              result.put("msg","验证码错误");
        }else{
            try{
                memberService.createMember(username,password,nickname);
                result.put("code","0");
                result.put("msg","success");

            }catch (BussinessException e){
                 e.printStackTrace();
                result.put("code",e.getCode());
                result.put("msg",e.getMsg());

            }
        }
        return  result;
    }

    @GetMapping("/login.html")
    public ModelAndView showLogin(){

        return new ModelAndView("/login");
    }


    @PostMapping("/check_login")
    @ResponseBody
    public Map checkLogin(String username, String password, String vc, HttpSession session){

        Map result=new HashMap();

        //获取图片显示验证码
        String verifyCode =(String) session.getAttribute("kaptchaVerifyCode");

        //与输入验证码比对
        if(vc==null || verifyCode==null || !vc.equalsIgnoreCase(verifyCode)) {
            result.put("Ecode", "VcError1");
            result.put("msg", "验证码错误");
        }else {

            try {
                Member member = memberService.checkLogin(username, password);

                session.setAttribute("loginMember",member);

                result.put("code","0");
                result.put("msg","success");

            }catch (BussinessException ex){
                ex.printStackTrace();
                result.put("code",ex.getCode());
                result.put("msg",ex.getMsg());
            }

        }
        return result;

    }

    @PostMapping("/update_read_state")
    @ResponseBody
    public Map updateReadState(Long memberId,Long bookId,Integer readState){
        Map result=new HashMap();

        try {
            memberService.updateMemberReadState(memberId,bookId,readState);
           result.put("code","0");
           result.put("msg","success");
        }catch (BussinessException ex){
            ex.printStackTrace();
            result.put("code",ex.getCode());
            result.put("msg",ex.getMsg());
        }
        return  result;
    }

    @PostMapping("/evaluate")
    @ResponseBody
    public Map evaluation(Long memberId,Long bookId,Integer score,String content){
        Map result=new HashMap();

        try {
            Evaluation eva = memberService.evaluate(memberId, bookId, score, content);
            result.put("code","0");
            result.put("msg","success");
            result.put("evaluation",eva);
        }catch (BussinessException ex){
            ex.printStackTrace();
            result.put("code",ex.getCode());
            result.put("msg",ex.getMsg());
        }


        return result;
    }


    @PostMapping("/enjoy")
    @ResponseBody
    public Map enjoy(Long evaluationId){

        Map result = new HashMap();
        try {
            Evaluation eva = memberService.enjoy(evaluationId);
            result.put("code", "0");
            result.put("msg", "success");
            result.put("evaluation", eva);
        }catch(BussinessException ex){
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }

}
