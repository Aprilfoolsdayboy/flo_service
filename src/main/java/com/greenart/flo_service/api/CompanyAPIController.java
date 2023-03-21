package com.greenart.flo_service.api;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.greenart.flo_service.service.CompanyService;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/company")
public class CompanyAPIController {
    @Autowired CompanyService companyService;
    @GetMapping("/list")
    public ResponseEntity<Object> getCompanyList(@RequestParam @Nullable String keyword , HttpSession session,
    @PageableDefault(size=5 ,sort="seq" , direction = Sort.Direction.DESC) Pageable pageable ) {
        if(keyword == null)keyword="";
        // model.addAttribute("result", companyService.getCompanyList(keyword, pageable));
        // model.addAttribute("keyword", keyword);
        return new ResponseEntity<>(companyService.getCompanyList(keyword, pageable),HttpStatus.OK);
    }
    @GetMapping("/detail")
    public ResponseEntity<Object> getCompanyDetail (@RequestParam Long no, @RequestParam @Nullable Integer page,@RequestParam @Nullable String keyword) {
        if(page == null) page = 0;
        if(keyword == null) keyword = " ";
        Map<String,Object> map = companyService.selectCompanyInfo(no);
        // map.put("message", null);
        // model.addAttribute("company", map);
        // model.addAttribute("page", page);
        // model.addAttribute("keyword", keyword);
        if((Boolean)map.get("status")) {
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/{no}/{name}")
    public ResponseEntity<Object> postCompanyUpdate(@PathVariable Long no,@PathVariable String name){
        Map <String, Object> resultMap = companyService.updatedCompanyInfo(no, name);
        return new ResponseEntity<>(resultMap,HttpStatus.ACCEPTED);
    }
    @PutMapping("/{name}")
    public ResponseEntity<Object> postCompanyAdd (@PathVariable String name) {
        Map <String, Object> resultMap = companyService.addCompanyInfo(name);
        return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
    }
     @DeleteMapping("/{no}")
    public ResponseEntity<Object> getCompanyDelete (@PathVariable Long no) {
        Map<String,Object> map =  new LinkedHashMap<String, Object>();
        companyService.deleteCompany(no);
        map.put("message", "장르 정보를 삭제했습니다.");
        return new ResponseEntity<>(map,HttpStatus.ACCEPTED);
        
    }
}
