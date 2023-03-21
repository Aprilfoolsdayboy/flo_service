package com.greenart.flo_service.vo.artistgroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteResponseVO {
    private Boolean status;
    private String message;
    @JsonIgnore
    private HttpStatus code;
}
