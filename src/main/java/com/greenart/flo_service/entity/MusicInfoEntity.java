package com.greenart.flo_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "music_info")
public class MusicInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_seq") private Long musicSeq;
    @Column(name = "music_name") private String musicName;
    @Column(name = "music_is_title") private Boolean musicIsTitle;
    @Column(name = "music_order") private Integer musicOrder;
    @Column(name = "music_genre_seq") private Integer musicGenreSeq;
    @Column(name = "music_ai_seq") private Integer musicAiSeq;
}
