package com.greenart.flo_service.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.greenart.flo_service.entity.GenreEntity;
import com.greenart.flo_service.repository.GenreRepository;
import com.greenart.flo_service.vo.GenreListResponseVO;

import jakarta.transaction.Transactional;

@Service
public class GenreService {
    @Autowired GenreRepository genreRepository;
        public GenreListResponseVO getGenreList(String keyword, Pageable pageable) {
        Page<GenreEntity> page = genreRepository.findByNameContains(keyword, pageable);
        GenreListResponseVO response = GenreListResponseVO.builder().list(page.getContent())
        .total(page.getTotalElements()).totalPage(page.getTotalPages()).currentPage(page.getNumber()).build();
        // Map<String,Object> map = new LinkedHashMap<String, Object>();
        // map.put("list", page.getContent());
        // map.put("total", page.getTotalElements());
        // map.put("totalpage", page.getTotalPages());
        // map.put("currentPage", page.getNumber());
        return response;  
    }
    public Map<String,Object> addGenreInfo(String name) {
        Map<String,Object> resultMap =  new LinkedHashMap<String, Object>();
         if(genreRepository.countByName(name) == 0 ) {
            GenreEntity entity = GenreEntity.builder().name(name).build();
            genreRepository.save(entity);
            resultMap.put("status", true);
            resultMap.put("message", "장르정보를 추가하였습니다.");
        }
        else{
        resultMap.put("status", false);
        resultMap.put("message", name+"장르는 이미 존재합니다.");
        }
        return resultMap;
    }
    @Transactional
    public void deleteGenre(Long genre_no) {
        genreRepository.deleteById(genre_no);
    }
    public Map<String,Object> selectGenreInfo (Long genre_no) {
        Map<String,Object> resultMap = new LinkedHashMap<String, Object>();
        Optional<GenreEntity> entityOpt = genreRepository.findById(genre_no);
        if(entityOpt.isEmpty()) {
            resultMap.put("status", false);
        }
        else{
            resultMap.put("status", true);
            resultMap.put("no", entityOpt.get().getSeq());
            resultMap.put("name", entityOpt.get().getName());

        }
        return resultMap;

    }
    public Map<String, Object> updatedGenreInfo(Long no, String name) {
        Map<String,Object> resultMap = new LinkedHashMap<String, Object>();
        Optional<GenreEntity> entityOpt = genreRepository.findById(no);
        if(entityOpt.isEmpty()) {
            resultMap.put("status",false);
            resultMap.put("no",no);
            resultMap.put("name",name);
            resultMap.put("message","잘못된 장르 정보입니다.");
        }
        else if (entityOpt.get().getName().equalsIgnoreCase(name)) {
            resultMap.put("status",false);
            resultMap.put("no",no);
            resultMap.put("name",name);
            resultMap.put("message","기존 설정된 이름으로 변경 불가능합니다.");
        }
        else if (genreRepository.countByName(name) !=0){
            resultMap.put("status",false);
            resultMap.put("no",no);
            resultMap.put("name",name);
            resultMap.put("message", name+"장르는 이미 존재합니다.");
        }
        else{
            GenreEntity entity = GenreEntity.builder().seq(no).name(name).build();
            genreRepository.save(entity);
            resultMap.put("status",true);
      
        }

        return resultMap;

    }
}
