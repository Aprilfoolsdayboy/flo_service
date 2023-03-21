package com.greenart.flo_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.greenart.flo_service.entity.GenreEntity;


public  interface GenreRepository  extends JpaRepository <GenreEntity ,Long> {
    public Page<GenreEntity> findByNameContains(String genreName, Pageable pageable);
    public Integer countByName (String genreName);
}
