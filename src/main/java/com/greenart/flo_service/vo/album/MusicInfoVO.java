package com.greenart.flo_service.vo.album;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusicInfoVO {
    private  String name;
    private Boolean isTitle;
    private Integer order;
    private Long genre;
    private Long album;
    private MultipartFile file;

}
