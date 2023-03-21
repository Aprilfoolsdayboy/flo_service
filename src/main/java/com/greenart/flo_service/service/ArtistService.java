package com.greenart.flo_service.service;

import com.greenart.flo_service.entity.ArtistEntity;
import com.greenart.flo_service.entity.ArtistGroupInfoEntity;
import com.greenart.flo_service.entity.CompanyEntity;
import com.greenart.flo_service.repository.ArtistGroupInfoRepostiory;
import com.greenart.flo_service.repository.ArtistRepository;
import com.greenart.flo_service.repository.CompanyRepository;
import com.greenart.flo_service.vo.ArtisGroupInfoInsertVO;
import com.greenart.flo_service.vo.ArtistGroupResponseVO;
import com.greenart.flo_service.vo.artist.ArtistInfoInsertVO;
import com.greenart.flo_service.vo.artist.ArtistInfoResponseVO;
import com.greenart.flo_service.vo.artist.ArtistResponseVO;
import com.greenart.flo_service.vo.artistgroup.ArtistGroupInfoResponseVo;
import com.greenart.flo_service.vo.artistgroup.DeleteResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final FileService fileService;
    private final ArtistGroupInfoRepostiory agiRepo;
    private final ArtistRepository artRepo;
    private final CompanyRepository companyRepository;
    public Map<String, Object> addArtistInfo(ArtistInfoInsertVO data, MultipartFile img){
        Map<String, Object > resultMap = new LinkedHashMap<String, Object>();
        String savedFilePath = "";
        try {
            savedFilePath = fileService.saveImageFile("artist", img);

        } catch (Exception e) {
            System.out.println("파일 전송 실패");
            resultMap.put("status",false);
            resultMap.put("message","파일 전송에 실패했습니다.");
            resultMap.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
            return  resultMap;
        }
        ArtistEntity entity =  ArtistEntity.builder().artName(data.getArtName())
                .artBirth(data.getBirthYear())
                .group(agiRepo.findById(data.getGrpNo()).get())
                .artImg(savedFilePath)
                .build();
        artRepo.save(entity);
        resultMap.put("status",true);
        resultMap.put("message","아티스트가 추가되었습니다.");
        resultMap.put("code",HttpStatus.ACCEPTED);

        return resultMap;
    }
    public ArtistResponseVO getArtistList(String keyword, Pageable pageable) {
        if (keyword == null) keyword = "";
        Page <ArtistEntity> artPage = artRepo.findByArtNameContains(keyword, pageable);
        ArtistResponseVO response = ArtistResponseVO.builder().list(artPage.getContent())
                .total(artPage.getTotalElements())
                .totalPage(artPage.getTotalPages())
                .currentPage(artPage.getNumber())
                .build();
        return response;
    }
    public DeleteResponseVO deleteArtistInfo (Long grpNo) {
        Optional<ArtistEntity> entityOpt = artRepo.findById(grpNo);
        if(entityOpt.isEmpty()) {
            return  DeleteResponseVO.builder().status(false).message("등록되지 않은 아티스트입니다").code(HttpStatus.BAD_REQUEST).build();
        }
        else {
            String filename = entityOpt.get().getArtImg();
            Boolean deleted = fileService.deleteImageFile("artist" , filename);
            String message = "아티스트 정보를 삭제했습니다.";
            if(deleted) message +="(이미지 삭제 완료)";
            else message += "(이미지 삭제 실패)";
            artRepo.delete(entityOpt.get());
            return  DeleteResponseVO.builder().status(true).message(message).code(HttpStatus.OK).build();
        }

    }
    public ArtistInfoResponseVO getArtistInfo (Long artNo) {
        Optional<ArtistEntity> entity = artRepo.findById(artNo);
        if(entity.isEmpty()) {
            return null;
        }
        else {
            return  ArtistInfoResponseVO.builder()
                    .no(entity.get().getArtSeq())
                    .name(entity.get().getArtName())
                    .birthYear(entity.get().getArtBirth())
                    .group(entity.get().getGroup())
                    .imgFile(entity.get().getArtImg()).build();
        }
    }
    public Map<String,Object> updateArtistInfo (ArtistInfoInsertVO data, MultipartFile img) throws Exception {
        Map<String, Object> map = new LinkedHashMap<>();
        Optional<ArtistEntity> entityOpt = artRepo.findById(data.getArtNo());
        if(entityOpt.isEmpty()) {
            map.put("status" ,false);
            map.put("message" ,"아티스트 정보를 찾을 수 없습니다.");
            map.put("code" ,HttpStatus.BAD_REQUEST);
            return map;
        }
        ArtistEntity entity = entityOpt.get();
        if(img != null) {
            String filename = fileService.saveImageFile("artist",img);
            fileService.deleteImageFile("artist",entity.getArtImg());
            entity.setArtImg(filename);
        }
        if(data.getArtName() != null &&!data.getArtName().equals("")){
            entity.setArtName(data.getArtName());
        }
        if(data.getBirthYear() !=null) {
            entity.setArtBirth(data.getBirthYear());
        }
        if(data.getGrpNo() !=null) {
            Optional<ArtistGroupInfoEntity> groupOpt = agiRepo.findById(data.getGrpNo());
            if(!groupOpt.isEmpty()){
                entity.setGroup(groupOpt.get());
            }
        }
        artRepo.save(entity);
        map.put("status" ,true);
        map.put("message" ,"아티스트 정보 수정 완료.");
        map.put("code" ,HttpStatus.ACCEPTED);
        return  map;
    }



}
