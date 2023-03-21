package com.greenart.flo_service.service;

import com.greenart.flo_service.entity.AdminEntity;
import com.greenart.flo_service.repository.AdminRepository;
import com.greenart.flo_service.security.provider.JwtTokenProvider;
import com.greenart.flo_service.security.service.CustomUserDetailService;
import com.greenart.flo_service.utils.AESAlgorithm;
import com.greenart.flo_service.vo.AdminInfoVO;
import com.greenart.flo_service.vo.LoginVO;
import com.greenart.flo_service.vo.admin.response.AdminLoginResponseVO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // 클래스 내부에 있는 private final 지정된 객체들의 의존성 주입
public class AdminSecurityService {
    private final AdminRepository adminRepository;
    private final AuthenticationManagerBuilder authBulider;
    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailService userDetailService;

    public AdminLoginResponseVO login(LoginVO login) {
//        login.setPwd(AESAlgorithm.Encrypt(login.getPwd()));
        AdminEntity admin = adminRepository.findByAdminIdAndAdminPwd(login.getId(),login.getPwd());
        if(admin == null) {
            return AdminLoginResponseVO.builder().status(false).message("아이디 또는 비밀번호 오류입니다.").cod(HttpStatus.FORBIDDEN).build();

        }
        else if (!admin.isEnabled()) {
            return AdminLoginResponseVO.builder().status(true).message("이용정지된 사용자입니다.").cod(HttpStatus.FORBIDDEN).build();

        }
        UsernamePasswordAuthenticationToken  authenticationToken
                = new UsernamePasswordAuthenticationToken(admin.getAdminId(),admin.getAdminPwd());
        System.out.println(authenticationToken);
        Authentication authentication = authBulider.getObject().authenticate(authenticationToken);
        System.out.println(authentication);
        return AdminLoginResponseVO.builder().status(true).message("로그인 성공").token(tokenProvider.
                        generateToken(authentication)).cod(HttpStatus.OK).build();
    }
    public AdminInfoVO getAdminDetailInfo(String id) {
        try {
            // Exception발생 시 id에 해당하는 사용자가 시큐리티에 등록되어있지 않은 상태
            userDetailService.loadUserByUsername(id);
            AdminEntity entity = adminRepository.findByAdminId(id);
            AdminInfoVO vo = new AdminInfoVO(entity);
            return vo;
        }
        catch (UsernameNotFoundException e) {
            return  null;
        }
    }

}
