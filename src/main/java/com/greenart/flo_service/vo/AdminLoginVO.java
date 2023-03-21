package com.greenart.flo_service.vo;

import lombok.Data;
// 관리자 로그인 Request 변수
@Data
public class AdminLoginVO {
    private String admin_id;
    private String admin_pwd;
}
