package com.greenart.flo_service.api;

import com.greenart.flo_service.service.AdminSecurityService;
import com.greenart.flo_service.vo.AdminInfoVO;
import com.greenart.flo_service.vo.LoginVO;
import com.greenart.flo_service.vo.admin.response.AdminLoginResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminAPIController {
    private final AdminSecurityService adminSecurityService;
    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponseVO> postAdminLogin(@RequestBody LoginVO login) {
        AdminLoginResponseVO response = adminSecurityService.login(login);
        return new ResponseEntity<>(response,response.getCod());
    }
    @GetMapping("/details/{id}")
    public ResponseEntity<AdminInfoVO> getAdminDetailInfo(@PathVariable String id) {
        return new ResponseEntity<>(adminSecurityService.getAdminDetailInfo(id), HttpStatus.OK);
    }
}
