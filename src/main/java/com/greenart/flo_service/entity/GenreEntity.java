package com.greenart.flo_service.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "genre_info")
@Builder
public class GenreEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "genre_seq") 
    @Schema(description = "장르번호" , example = "1")
    private Long seq;
    @Column(name= "genre_name") 
    @Schema(description = "장르 명" , example = "K-POP")
    private String name;
    
    
}
