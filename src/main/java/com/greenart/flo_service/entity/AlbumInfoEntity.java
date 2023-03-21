package com.greenart.flo_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "album_info")
public class AlbumInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ai_seq") private Long aiSeq;
    @Column(name = "ai_img") private String aiImg;
    @Column(name = "ai_name") private String aiName;
    @Column(name = "ai_pub_dt") private LocalDate aiPubDt;
    @Column(name = "ai_foreign") private Integer aiForeign;
    @Column(name = "ai_introduce") private String aiIntroduce;
    @Column(name = "ai_agi_seq") private Long aiAgiSeq;
}
