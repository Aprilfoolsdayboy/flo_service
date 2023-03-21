package com.greenart.flo_service.vo;

import com.greenart.flo_service.entity.CompanyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtisGroupInfoInsertVO {
    private Long grpNo;
    private String name;
    private Integer debutYear;
    private Long company;
    
    
}
