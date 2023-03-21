package com.greenart.flo_service.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenart.flo_service.service.CompanyService;


import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/company")
public class CompanyController {
    @Autowired CompanyService companyService;
    @GetMapping("/list")
    public String getCompanyList(Model model, @RequestParam @Nullable String keyword , HttpSession session,
    @PageableDefault(size=5 ,sort="pubSeq" , direction = Sort.Direction.ASC) Pageable pageable ) {
        if(keyword == null)keyword="";
        model.addAttribute("result", companyService.getCompanyList(keyword, pageable));
        model.addAttribute("keyword", keyword);
        return "/company/list";
    }
    @GetMapping("/add")
    public String getCompanyAdd() {
        return "/company/add";
    }

    @PostMapping("/add")
    // html에서 name이 name이기 때문
    public String postCompanyAdd (String name,Model model) {
        Map <String, Object> resultMap = companyService.addCompanyInfo(name);
        if((Boolean)resultMap.get("status")) {
            return "redirect:/company/list";
        }
        else{
            model.addAttribute("name", name);
            model.addAttribute("result", resultMap);
            return "/company/add";
        }
    }
    @GetMapping("/delete") 
    public String getCompanyDelete (@RequestParam Long company_no) {
        companyService.deleteCompany(company_no);
        return "redirect:/company/list";
        
    }
    @GetMapping("/detail")
    public String getCompanyDetail (@RequestParam Long company_no, Model model,@RequestParam @Nullable Integer page,@RequestParam String keyword) {
        if(page == null) page = 0;
        if(keyword == null) keyword = "";
        Map<String,Object> map = companyService.selectCompanyInfo(company_no);
        map.put("message", null);
        model.addAttribute("company", map);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "/company/detail";
    }
    @PostMapping("/update")
    public String postCompanyUpdate(Long no,String name, Model model){
        Map <String, Object> resultMap = companyService.updatedCompanyInfo(no,name);
        if((Boolean)resultMap.get("updated")) {
            return "redirect:/company/list";
        }
        else{
         resultMap.put("status",true);
         model.addAttribute("company", resultMap);
            return "/company/detail";
        }
    }
}
