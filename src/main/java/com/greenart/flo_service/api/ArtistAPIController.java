package com.greenart.flo_service.api;

import com.greenart.flo_service.service.ArtistService;
import com.greenart.flo_service.vo.ArtisGroupInfoInsertVO;
import com.greenart.flo_service.vo.ArtistGroupResponseVO;
import com.greenart.flo_service.vo.artist.ArtistInfoInsertVO;
import com.greenart.flo_service.vo.artist.ArtistInfoResponseVO;
import com.greenart.flo_service.vo.artist.ArtistResponseVO;
import com.greenart.flo_service.vo.artistgroup.ArtistGroupInfoResponseVo;
import com.greenart.flo_service.vo.artistgroup.DeleteResponseVO;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/artist/info")
@RequiredArgsConstructor
public class ArtistAPIController {
    private  final ArtistService artistService;
    @PutMapping(value = "", consumes= MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> putArtistInfo(
            @Parameter(description = "multipart/formdata 로 데이터를 입력합니다(name:그룹명 , debutYear:데뷔연도, company: 기획사 번호)")
            ArtistInfoInsertVO data,
            @Parameter(description = "multipart/formdata 로 파일을 입력합니다")
            MultipartFile img) {
        Map<String, Object> map = artistService.addArtistInfo(data, img);
        return new ResponseEntity<>(map, HttpStatus.OK);

    }
    @GetMapping("list")
    public ResponseEntity<ArtistResponseVO> getArtistList
            (@RequestParam @Nullable String keyword,
             @PageableDefault(size = 10 , sort = "artSeq",direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(artistService.getArtistList(keyword, pageable), HttpStatus.OK);
    }
    @DeleteMapping("/{artNo}")
    public ResponseEntity<DeleteResponseVO> deleteArtist(@PathVariable Long artNo) {
        DeleteResponseVO response = artistService.deleteArtistInfo(artNo);
        return new ResponseEntity<>(response, response.getCode());
    }
    @GetMapping("/{artNo}")
    public ResponseEntity<ArtistInfoResponseVO> getArtistDetail (@PathVariable Long artNo) {
        ArtistInfoResponseVO response = artistService.getArtistInfo(artNo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("")
    public ResponseEntity<Object> updateArtist(ArtistInfoInsertVO data , @Nullable MultipartFile img) throws Exception {
        Map<String,Object> resultMap = artistService.updateArtistInfo(data, img);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

}
