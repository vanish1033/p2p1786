package com.bjpowernode.p2p.controller;

import com.bjpowernode.p2p.model.loan.LoanInfo;
import com.bjpowernode.p2p.model.vo.PaginatinoVO;
import com.bjpowernode.p2p.service.LoanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * 产品数据
 */
@Controller
public class LoanInfoController {

    @Autowired
    LoanInfoService loanInfoService;

    @RequestMapping("/loan/loan")
    private String loan(Integer ptype, Integer currentPage, Model model) {


        //建一个map装分页参数
        Map<String, Object> paramMap = new HashMap<>();

        //如果当前页码为null，就默认第一页
        if (currentPage == null) {
            currentPage = 1;
        }
        //定义每页显示几条数据
        Integer pageSize = 9;

        //产品类型，如果不为null就传过去
        if (ptype != null) {
            paramMap.put("productType", ptype);
        }

        paramMap.put("currentPage", (currentPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);

        //根据分页参数和产品类型进行查询
        PaginatinoVO<LoanInfo> infoPaginatinoVO = loanInfoService.queryLoanInfoByPage(paramMap);
        //计算总页数，向上取整
        Integer totalPage = (int) Math.ceil(infoPaginatinoVO.getTotal().intValue() / pageSize);

        //总记录数
        model.addAttribute("totalRows", infoPaginatinoVO.getTotal());
        //总页数
        model.addAttribute("totalPage", totalPage);
        //每页显示的数据
        model.addAttribute("loanInfoList", infoPaginatinoVO.getDataList());
        //当前页码
        model.addAttribute("currentPage", currentPage);
        //产品类型
        if (ptype != null) {
            model.addAttribute("productType", ptype);
        }

        return "loan";
    }

}
