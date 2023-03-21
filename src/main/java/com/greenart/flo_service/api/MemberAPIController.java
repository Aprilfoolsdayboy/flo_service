package com.greenart.flo_service.api;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.flo_service.entity.MemberInfoEntity;
import com.greenart.flo_service.repository.MemberInfoRepository;
import com.greenart.flo_service.vo.LoginVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor // @Autowired 자동으로 해줌
public class MemberAPIController {
    private final MemberInfoRepository memberInfoRepository;

    @PostMapping("/login")
    public Map<String, Object> postMemberLogin(@RequestBody LoginVO login) {
        Map<String, Object> resultmap = new LinkedHashMap<String, Object>();

        MemberInfoEntity entity = memberInfoRepository.findByMiIdAndMiPwd(login.getId(), login.getPwd());

        if (entity == null) {
            resultmap.put("status", false);
            resultmap.put("message", "잘못된 로그인 정보입니다.");
        }
        else {
            resultmap.put("status", true);
            resultmap.put("user", entity);
        }
        return resultmap;

    }
    
}
