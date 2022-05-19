package com.zy.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.entity.Book;
import com.zy.entity.Evaluation;
import com.zy.entity.Member;
import com.zy.service.BookService;
import com.zy.service.EvaluationService;
import com.zy.service.MemberService;
import com.zy.utils.BussinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management/evaluation")
public class MEvaluationController {
    @Resource
    private EvaluationService evaluationService;


    @Resource
    private BookService bookService;

    @Resource
    private MemberService memberService;


    @GetMapping("/comment.html")
    public ModelAndView showEvaluation() {
        return new ModelAndView("/management/evaluation");
    }


    @GetMapping("/list")
    @ResponseBody
    public Map list(Integer page, Integer limit) {
        Map result = new HashMap();

        if (page == null) {
            page = 1;

        }
        if (limit == null) {
            limit = 10;
        }


        try {
            IPage<Evaluation> objPage = evaluationService.pagination(page, limit);

            Long bookId;
            Long memberId;

            for (Evaluation eva : objPage.getRecords()) {
                bookId = eva.getBookId();
                memberId = eva.getMemberId();

                Book commentBook = bookService.selectById(bookId);

                Member commentMember = memberService.selectById(memberId);

                eva.setBook(commentBook);
                eva.setMember(commentMember);
            }


            result.put("code", "0");
            result.put("msg", "success");
            result.put("data", objPage.getRecords());
            result.put("count", objPage.getTotal());

            return result;

        } catch (BussinessException e) {
            e.printStackTrace();
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
            return result;
        }



    }

    @PostMapping("/disable")
    @ResponseBody
    public Map disable(Long evaluationId,String reason){

        Map result=new HashMap();
        try {
        Evaluation eva = evaluationService.selectByEvaluationId(evaluationId);


        eva.setDisableReason(reason);
        eva.setState("disable");



            evaluationService.updateDisable(eva);
            result.put("code","0");
            result.put("msg","success");
        } catch (BussinessException e) {
            e.printStackTrace();
            result.put("code",e.getCode());
            result.put("msg",e.getMsg());
        }

            return result;
    }


}
