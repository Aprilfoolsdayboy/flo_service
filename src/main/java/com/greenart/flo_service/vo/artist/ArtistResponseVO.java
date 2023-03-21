package com.greenart.flo_service.vo.artist;

import com.greenart.flo_service.entity.ArtistEntity;
import com.greenart.flo_service.entity.ArtistGroupInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArtistResponseVO {
    private List<ArtistEntity> list;
    private Long total;
    private Integer totalPage;
    private Integer currentPage;
}
