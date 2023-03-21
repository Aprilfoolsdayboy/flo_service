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
@Table(name = "music_file_info")
public class MusicFileEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mfile_seq") private Long mfileSeq;
    @Column(name = "mfile_name") private String mfileName;
    @Column(name = "mfile_type") private String mfileType;
    @Column(name = "mfile_music_seq") private Long mfileMusicSeq;
}
