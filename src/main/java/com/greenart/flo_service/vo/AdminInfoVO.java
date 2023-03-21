package com.greenart.flo_service.vo;

import com.greenart.flo_service.entity.AdminEntity;

import lombok.Data;
// 관리자 로그인 정보 변수
@Data
public class AdminInfoVO {
    private Long admin_no;
    private String admin_id;
    private String admin_name;
    private Integer admin_status;
    private Integer admin_grade;
    
    public AdminInfoVO(AdminEntity entity) {
        this.admin_no = entity.getAdminSeq(); 
        this.admin_id = entity.getAdminId();
        this.admin_name = entity.getAdminName();
        this.admin_status = entity.getAdminStatus();
        this.admin_grade = entity.getAdminGrade();
    }
}
