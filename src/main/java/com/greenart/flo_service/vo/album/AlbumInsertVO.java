package com.greenart.flo_service.vo.album;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumInsertVO {
    private AlbumInfoVO albumInfo;
    private MusicInfoVO[] musicList;
}
