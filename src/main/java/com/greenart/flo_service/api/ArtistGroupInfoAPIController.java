package com.greenart.flo_service.api;

import java.util.Map;

import com.greenart.flo_service.vo.artistgroup.ArtistGroupInfoResponseVo;
import com.greenart.flo_service.vo.artistgroup.DeleteResponseVO;
import org.springframework.http.MediaType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.greenart.flo_service.service.ArtistGroupInfoService;
import com.greenart.flo_service.vo.ArtisGroupInfoInsertVO;
import com.greenart.flo_service.vo.ArtistGroupResponseVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("api/artist/group")
@RequiredArgsConstructor
public class ArtistGroupInfoAPIController {
    private final ArtistGroupInfoService agiService;
    @Operation(summary = "아티스트 그룹 추가" ,description = "아티스트 그룹을 추가합니다.")
    @PutMapping(value = "", consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> putArtistGroupInfo(
        @Parameter(description = "multipart/formdata 로 데이터를 입력합니다(name:그룹명 , debutYear:데뷔연도, company: 기획사 번호)")
        ArtisGroupInfoInsertVO data, 
        @Parameter(description = "multipart/formdata 로 파일을 입력합니다")
        MultipartFile img) {
        Map<String, Object> map = agiService.addArtistGroupInfo(data, img);
        return new ResponseEntity<>(map, HttpStatus.OK);

    }
    @GetMapping("list")
    public ResponseEntity<ArtistGroupResponseVO> getArtistGroupList (@RequestParam @Nullable String keyword,
        @PageableDefault(size = 10 , sort = "agiSeq",direction = Sort.Direction.DESC) Pageable pageable) {
        
            return new ResponseEntity<>(agiService.getArtistGroupList(keyword, pageable), HttpStatus.OK);
        }
    @DeleteMapping("/{grpNo}")
    public ResponseEntity<DeleteResponseVO> deleteArtistGroup(@PathVariable Long grpNo) {
        DeleteResponseVO response = agiService.deleteArtistGroupInfo(grpNo);
        return new ResponseEntity<>(response, response.getCode());
    }
    @GetMapping("/{grpNo}")
    public ResponseEntity<ArtistGroupInfoResponseVo> getArtistGroupDetail (@PathVariable Long grpNo) {
        ArtistGroupInfoResponseVo response = agiService.getArtistGroupInfo(grpNo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("")
    public ResponseEntity<Object> updateArtistGroup(ArtisGroupInfoInsertVO data , @Nullable MultipartFile img) throws Exception {
        Map<String,Object> resultMap = agiService.updateArtistGroupInfo(data, img);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
