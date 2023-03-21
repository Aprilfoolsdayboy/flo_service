package com.greenart.flo_service.vo.admin.response;

import com.greenart.flo_service.security.vo.TokenVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminLoginResponseVO {
    private Boolean status;
    private  String message;
    private HttpStatus cod;
    private TokenVO token;
}
