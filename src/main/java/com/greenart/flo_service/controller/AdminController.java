package com.greenart.flo_service.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.greenart.flo_service.service.AdminService;
import com.greenart.flo_service.vo.AdminAddVO;
import com.greenart.flo_service.vo.AdminInfoVO;
import com.greenart.flo_service.vo.AdminLoginVO;
import com.greenart.flo_service.vo.AdminUpdateVO;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class AdminController {
    @Value("${flo.file.adminImg}") String adminImgPath;

    @Autowired AdminService adminService;
    @PostMapping("/login")
    public String postAdminLoging (AdminLoginVO login, HttpSession session, Model model) {
        Map <String, Object> resultMap = adminService.loginAdmin(login);
        if((boolean)resultMap.get("status")) {
            session.setAttribute("loginUser", resultMap.get("login"));
            return "redirect:/main";
        }
        else{
            model.addAttribute("message", resultMap.get("message"));
            return "/index";
        }
    }
    @GetMapping("/logout")
    public String getAdminLoging (HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    @GetMapping("/add")
    public String getAddAdmin() {
        return "/admin/add";
        }
    @PostMapping("/add")
    public String postAddAdmin(AdminAddVO data, Model model /* @RequestPart MultipartFile adminImg*/) {
       Map<String,Object> map = adminService.addAdmin(data);
       if((boolean)map.get("status")) {
        return "redirect:/";
       }
       model.addAttribute("inputdata",data);
       model.addAttribute("message",map.get("message"));
        return "/admin/add";
        }
    @GetMapping("/list")
    public String getAdminList(Model model, @RequestParam @Nullable String keyword , HttpSession session,
    @PageableDefault(size=5 ,sort="adminSeq" , direction = Sort.Direction.ASC) Pageable pageable ){
        // size = 한 페이지 당 출력할 row 수
        // sort = 정렬 기준이 될 엔터티 변수 명
        // direction : 정렬 방향 (오름차순 , 내림차순)
        session.setAttribute("update_result", null);
        AdminInfoVO admin = (AdminInfoVO)session.getAttribute("loginUser");
        if(admin == null) { // 로그인 상태가 아니라면
            return "redirect:/"; // 로그인 페이지로
        }
        else if (admin.getAdmin_grade() != 99) { // 로그인 했는데 마스터가 아니라면
            return "redirect:/main"; // 메인페이지로
        }
        if(keyword == null)keyword="";
        model.addAttribute("result", adminService.getAdminList(keyword, pageable));
        model.addAttribute("keyword", keyword);
        return "/admin/list";

    }

    @GetMapping("/update/status")
    public String getAdminUpdateStatus (@RequestParam Integer value,@RequestParam Long admin_no,
    @RequestParam Integer page, @RequestParam @Nullable String keyword , HttpSession session) {
         AdminInfoVO admin = (AdminInfoVO)session.getAttribute("loginUser");
        if(admin == null) { // 로그인 상태가 아니라면
            return "redirect:/"; // 로그인 페이지로
        }
        else if (admin.getAdmin_grade() != 99) { // 로그인 했는데 마스터가 아니라면
            return "redirect:/main"; // 메인페이지로
        }
        adminService.updateAdminStatus(value,admin_no);
        String returnValue = "";
        if(keyword == null || keyword.equals("")) returnValue = "redirect:/admin/list?page="+page;
        else returnValue = "redirect:/admin/list?page="+page+"&keyword="+keyword;
        return returnValue;
    }
    @GetMapping ("/delete") 
    public String getAdminDelete(@RequestParam Long admin_no,@RequestParam Integer page, @RequestParam @Nullable String keyword,HttpSession session) {
        AdminInfoVO admin = (AdminInfoVO)session.getAttribute("loginUser");
        if(admin == null) { // 로그인 상태가 아니라면
            return "redirect:/"; // 로그인 페이지로
        }
        else if (admin.getAdmin_grade() != 99) { // 로그인 했는데 마스터가 아니라면
            return "redirect:/main"; // 메인페이지로
        }
        adminService.deleteAdmin(admin_no);
        String returnValue = "";
        if(keyword == null || keyword.equals("")) returnValue = "redirect:/admin/list?page="+page;
        else returnValue = "redirect:/admin/list?page="+page+"&keyword="+keyword;
        return returnValue;
    }
    @GetMapping("/detail")
    public String getAdminDetail (@RequestParam Long admin_no, Model model,HttpSession session) {
        model.addAttribute("admin_detail", adminService.getAdminInfo(admin_no));
        AdminInfoVO admin = (AdminInfoVO)session.getAttribute("loginUser");
        if(admin == null) { // 로그인 상태가 아니라면
            return "redirect:/"; // 로그인 페이지로
        }
        else if (admin.getAdmin_grade() != 99) { // 로그인 했는데 마스터가 아니라면
            return "redirect:/main"; // 메인페이지로
        }
        return "/admin/detail";
    }
    @PostMapping("/update")
    public String postAdminUpdate(HttpSession session, AdminUpdateVO data) {
        System.out.println(data);
        Map <String,Object> map = adminService.updateAdminInfo(data);
        if((boolean) map.get("status")) {
            return "redirect:/admin/list";
        }
        session.setAttribute("update_result", map);
        return "redirect:/admin/detail?admin_no="+data.getSeq();

    }
}
