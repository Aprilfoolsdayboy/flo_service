package com.greenart.flo_service.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import com.greenart.flo_service.entity.CompanyEntity;
import com.greenart.flo_service.vo.artistgroup.ArtistGroupInfoResponseVo;
import com.greenart.flo_service.vo.artistgroup.DeleteResponseVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.greenart.flo_service.entity.ArtistGroupInfoEntity;
import com.greenart.flo_service.repository.ArtistGroupInfoRepostiory;
import com.greenart.flo_service.repository.CompanyRepository;
import com.greenart.flo_service.vo.ArtisGroupInfoInsertVO;
import com.greenart.flo_service.vo.ArtistGroupResponseVO;

import aj.org.objectweb.asm.Type;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // 의존성 자동주의 @Autowired 자롱드로 해줌
public class ArtistGroupInfoService {
    private final FileService fileService;
    private final ArtistGroupInfoRepostiory agiRepo;
    private final CompanyRepository companyRepository;
    public Map <String, Object> addArtistGroupInfo(ArtisGroupInfoInsertVO data, MultipartFile img){
        Map<String, Object > resultMap = new LinkedHashMap<String, Object>();
        String savedFilePath = "";
        try { 
            savedFilePath = fileService.saveImageFile("artist_group", img);
            
        } catch (Exception e) {
            System.out.println("파일 전송 실패");
            resultMap.put("status",false);
            resultMap.put("message","파일 전송에 실패했습니다.");
            resultMap.put("code",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        ArtistGroupInfoEntity entity =  ArtistGroupInfoEntity.builder().agiName(data.getName())
        .agiDebutYear(data.getDebutYear())
        .company(companyRepository.findById(data.getCompany()).get())
        // .agiPciSeq(data.getCompany())
        .agiImg(savedFilePath)
        .build();
        agiRepo.save(entity);
        resultMap.put("status",true);
        resultMap.put("message","아티스트 그룹이 추가되었습니다.");
        resultMap.put("code",HttpStatus.ACCEPTED);
        
        return resultMap;

        
    }
    public ArtistGroupResponseVO getArtistGroupList(String keyword, Pageable pageable) {
        if (keyword == null) keyword = "";
        Page <ArtistGroupInfoEntity> agiPage = agiRepo.findByAgiNameContains(keyword, pageable);
        ArtistGroupResponseVO response = ArtistGroupResponseVO.builder().list(agiPage.getContent())
        .total(agiPage.getTotalElements())
        .totalPage(agiPage.getTotalPages())
        .currentPage(agiPage.getNumber())
        .build();
        return response;
    }

    public DeleteResponseVO deleteArtistGroupInfo (Long grpNo) {
        Optional<ArtistGroupInfoEntity> entity = agiRepo.findById(grpNo);
        if(entity.isEmpty()) {
            return  DeleteResponseVO.builder().status(false).message("등록되지 않은 그룹입니다").code(HttpStatus.BAD_REQUEST).build();
        }
        else {
            String filename = entity.get().getAgiImg();
            Boolean deleted = fileService.deleteImageFile("artist_group" , filename);
            String message = "아티스트 그룹 정보를 삭제했습니다.";
            if(deleted) message +="(이미지 삭제 완료)";
                    else message += "(이미지 삭제 실패)";
            agiRepo.delete(entity.get());
            return  DeleteResponseVO.builder().status(true).message(message).code(HttpStatus.OK).build();
        }

    }
    public ArtistGroupInfoResponseVo getArtistGroupInfo (Long grpNo) {
        Optional<ArtistGroupInfoEntity> entity = agiRepo.findById(grpNo);
        if(entity.isEmpty()) {
            return null;
        }
        else {
            return  ArtistGroupInfoResponseVo.builder()
                    .no(entity.get().getAgiSeq())
                    .name(entity.get().getAgiName())
                    .debutYear(entity.get().getAgiDebutYear())
                    .Company(entity.get().getCompany())
                    .imgFile(entity.get().getAgiImg()).build();
        }
    }

    public Map<String,Object> updateArtistGroupInfo (ArtisGroupInfoInsertVO data, MultipartFile img) throws Exception {
        Map<String, Object> map = new LinkedHashMap<>();
        Optional<ArtistGroupInfoEntity> entityOpt = agiRepo.findById(data.getGrpNo());
        if(entityOpt.isEmpty()) {
            map.put("status" ,false);
            map.put("message" ,"아티스트 그룹 정보를 찾을 수 없습니다.");
            map.put("code" ,HttpStatus.BAD_REQUEST);
            return map;
        }
        ArtistGroupInfoEntity entity = entityOpt.get();
        if(img != null) {
            String filename = fileService.saveImageFile("artist_group",img);
            fileService.deleteImageFile("artist_group",entity.getAgiImg());
            entity.setAgiImg(filename);
        }
        if(data.getName() != null &&!data.getName().equals("")){
            entity.setAgiName(data.getName());
        }
        if(data.getDebutYear() !=null) {
            entity.setAgiDebutYear(data.getDebutYear());
        }
        if(data.getCompany() !=null) {
            Optional<CompanyEntity> companyOpt = companyRepository.findById(data.getCompany());
            if(!companyOpt.isEmpty()){
                entity.setCompany(companyOpt.get());
            }
        }
        agiRepo.save(entity);
        map.put("status" ,true);
        map.put("message" ,"아티스트 그룹 정보 수정 완료.");
        map.put("code" ,HttpStatus.ACCEPTED);
        return  map;
    }
}
