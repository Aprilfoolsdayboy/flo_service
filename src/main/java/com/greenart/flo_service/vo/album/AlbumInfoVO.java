package com.greenart.flo_service.vo.album;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumInfoVO {
    private  String albumName;
    private LocalDate publishDate;
    private  Integer country;
    private String introduce;
    private MultipartFile albumImg;
}
