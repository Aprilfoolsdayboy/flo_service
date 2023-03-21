package com.greenart.flo_service.vo.artistgroup;

import com.greenart.flo_service.entity.CompanyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArtistGroupInfoResponseVo {
    private Long no;
    private String name;
    private Integer debutYear;
    private CompanyEntity Company;
    private String imgFile;
}
