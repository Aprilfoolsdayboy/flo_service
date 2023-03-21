package com.greenart.flo_service.vo.artist;

import com.greenart.flo_service.entity.ArtistGroupInfoEntity;
import com.greenart.flo_service.entity.CompanyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArtistInfoResponseVO {
    private Long no;
    private String name;
    private Integer birthYear;
    private ArtistGroupInfoEntity group;
    private String imgFile;
}
