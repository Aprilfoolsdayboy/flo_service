package com.greenart.flo_service.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.greenart.flo_service.entity.AdminEntity;
import com.greenart.flo_service.repository.AdminRepository;
import com.greenart.flo_service.vo.AdminAddVO;
import com.greenart.flo_service.vo.AdminInfoVO;
import com.greenart.flo_service.vo.AdminLoginVO;
import com.greenart.flo_service.vo.AdminUpdateVO;

@Service
public class AdminService {
    @Autowired AdminRepository adminRepository;
    public Map<String, Object> loginAdmin(AdminLoginVO login) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        AdminEntity entity = adminRepository.findByAdminIdAndAdminPwd(login.getAdmin_id(), login.getAdmin_pwd());
        if (entity == null) {
            map.put("status", false);
            map.put("message","아이디 혹은 비밀번호 오류입니다.");
        }
        else if (entity.getAdminStatus() == 1) {
          map.put("status", false);
          map.put("message","등록 대기중인 관리자 계정입니다.");
        }
        else if (entity.getAdminStatus() == 3) {
        map.put("status", false);
        map.put("message","이용 정지된 관리자 계정입니다.");
        }
        else {
        map.put("status", true);
        map.put("message","로그인 되었습니다.");
        map.put("login", new AdminInfoVO(entity));
    }
    return map;
    }


    public Map<String, Object> addAdmin(AdminAddVO data) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        if (data.getId() == null || data.getId().equals("")) {
            map.put("status", false);
            map.put("message", "아이디를 입력하세요");
        }
        else if(adminRepository.countByAdminId(data.getId()) !=0){
            map.put("status", false);
            map.put("message", data.getId()+"은/는 이미 사용중입니다.");
        }
        else if (data.getPwd() == null || data.getPwd().equals("")) {
            map.put("status", false);
            map.put("message", "비밀번호를 입력하세요");
        }
        else if (data.getName() == null || data.getName().equals("")) {
            map.put("status", false);
            map.put("message", "이름을 입력하세요");
        }
        else {
            AdminEntity entity = AdminEntity.builder().adminId(data.getId()).adminPwd(data.getPwd()).
            adminName(data.getName()).adminGrade(data.getType()).adminGrade(1).build();
            adminRepository.save(entity);
            map.put("status", true);
            map.put("message", "관리자 계정 등록 신청 완료");
        }

        return map;
    }

    public Map<String,Object> getAdminList(String keyword, Pageable pageable) {
        Page<AdminEntity> page = adminRepository.findByAdminIdContains(keyword, pageable);
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        map.put("list", page.getContent());
        map.put("total", page.getTotalElements());
        map.put("totalpage", page.getTotalPages());
        map.put("currentPage", page.getNumber());
        return map;
        
    }

       public void updateAdminStatus(Integer value, Long admin_no) {
        AdminEntity entity = adminRepository.findById(admin_no).get();
        entity.setAdminStatus(value);
        adminRepository.save(entity);
    }
    public void deleteAdmin (Long admin_no) {
        adminRepository.deleteById(admin_no);
    }
    public AdminEntity getAdminInfo (Long admin_no){
        return adminRepository.findById(admin_no).get();
    }
    public Map<String, Object> updateAdminInfo(AdminUpdateVO data) {
        Map <String,Object> resultMap = new LinkedHashMap<String, Object>();
        Optional <AdminEntity> entity = adminRepository.findById(data.getSeq());
        String pattern = "^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]*$"; // 특수문자 ,공백제외하고 허용(정규표현식)
 
        if(entity.isEmpty()) {
            resultMap.put("status", false);
            resultMap.put("message", "잘못된 사용자 번호가 입력되었습니다.");
        }
        else if (data.getPwd().length() > 16 || data.getPwd().length() < 8) {
            resultMap.put("status", false);
            resultMap.put("message", "비밀번호는  8 ~16자로 입력해주세요");
        }
        else if(data.getPwd().replaceAll(" ","").length() == 0 || !Pattern.matches(pattern,data.getPwd())) {
            resultMap.put("status", false);
            resultMap.put("message", "비밀번에 특수문자 또는 공백을 사용 할 수 없습니다.");  
        }
        else if (data.getName().replaceAll(" ","").length() == 0 || !Pattern.matches(pattern,data.getName())) {
            resultMap.put("status", false);
            resultMap.put("message", "관리자이름에 특수문자 또는 공백을 사용 할 수 없습니다.");  
        }
        else { 
            AdminEntity e  =  entity.get();
            e.setAdminPwd(data.getPwd());
            e.setAdminName(data.getName());
            e.setAdminStatus(data.getStatus());
            e.setAdminGrade(data.getGrade());
            adminRepository.save(e);
            resultMap.put("status", true);
        }
        return resultMap;
    }
}
