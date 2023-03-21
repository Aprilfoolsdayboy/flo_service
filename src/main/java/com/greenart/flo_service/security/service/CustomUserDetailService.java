package com.greenart.flo_service.security.service;

//import com.greenart.flo_service.mapper.MemberMapper;
//import com.greenart.flo_service.vo.entitiy.MemberInfoVO;


import com.greenart.flo_service.entity.AdminEntity;
import com.greenart.flo_service.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
//    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private  final  AdminRepository adminRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return createUserDetails(adminRepository.findByAdminId(username));
    }
    public UserDetails createUserDetails(AdminEntity member) {
        return User.builder().username(member.getAdminId())
                .password(passwordEncoder.encode(member.getAdminPwd()))
                .roles(member.getAdminRole())
                .build();
    }
}

