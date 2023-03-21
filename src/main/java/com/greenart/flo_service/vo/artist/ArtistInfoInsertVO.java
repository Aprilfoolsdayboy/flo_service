package com.greenart.flo_service.vo.artist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistInfoInsertVO {
    private Long artNo;
    private String artName;
    private Integer birthYear;
    private Long grpNo;

}
