package com.greenart.flo_service.vo;

import java.util.List;

import com.greenart.flo_service.entity.ArtistGroupInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArtistGroupResponseVO {
    private List <ArtistGroupInfoEntity> list;
    private Long total;
    private Integer totalPage;
    private Integer currentPage;
    
}
