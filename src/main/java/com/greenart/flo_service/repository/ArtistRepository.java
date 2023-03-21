package com.greenart.flo_service.repository;

import com.greenart.flo_service.entity.ArtistEntity;
import com.greenart.flo_service.entity.ArtistGroupInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository  extends JpaRepository <ArtistEntity ,Long> {
    Page<ArtistEntity> findByArtNameContains(String keyword, Pageable pageable);


}
