package com.greenart.flo_service.api;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.greenart.flo_service.service.GenreService;
import com.greenart.flo_service.vo.GenreListResponseVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpSession;
@Tag(name = "곡 장르 정보 관리", description = "장르정보 CRUD API")
@RestController
@RequestMapping("/api/genre")
public class GenreAPIController {
    @Autowired GenreService genreService;
    @Operation(summary = "장르 리스트",description = "등록된 장르정보를 10개 단위로 보여줍니다.")
    @PageableAsQueryParam
    @GetMapping("/list")
    public ResponseEntity<GenreListResponseVO > getGenreList(
        @Parameter(description = "검색어", example = "국악")
        @RequestParam @Nullable String keyword , HttpSession session,
        @Parameter(hidden = true)
        @PageableDefault(size=5 ,sort="seq" , direction = Sort.Direction.DESC) Pageable pageable ) {
        if(keyword == null)keyword="";
        // model.addAttribute("result", genreService.getGenreList(keyword, pageable));
        // model.addAttribute("keyword", keyword);
        return new ResponseEntity<>(genreService.getGenreList(keyword, pageable),HttpStatus.OK);
    }
    @GetMapping("/detail")
    public ResponseEntity<Object> getGenreDetail (@RequestParam Long no, @RequestParam @Nullable Integer page,@RequestParam @Nullable String keyword) {
        if(page == null) page = 0;
        if(keyword == null) keyword = " ";
        Map<String,Object> map = genreService.selectGenreInfo(no);
        // map.put("message", null);
        // model.addAttribute("genre", map);
        // model.addAttribute("page", page);
        // model.addAttribute("keyword", keyword);
        if((Boolean)map.get("status")) {
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/{no}/{name}")
    public ResponseEntity<Object> postGenreUpdate(@PathVariable Long no, @PathVariable String name){
        Map <String, Object> resultMap = genreService.updatedGenreInfo(no, name);
        return new ResponseEntity<>(resultMap,HttpStatus.ACCEPTED);
    }
    @PutMapping("/{name}")
    public ResponseEntity<Object> postGenreAdd (@PathVariable String name) {
        Map <String, Object> resultMap = genreService.addGenreInfo(name);
        return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
    }
     @DeleteMapping("/{no}")
    public ResponseEntity<Object> getGenreDelete (@PathVariable Long no) {
        Map<String,Object> map =  new LinkedHashMap<String, Object>();
        genreService.deleteGenre(no);
        map.put("message", "장르 정보를 삭제했습니다.");
        return new ResponseEntity<>(map,HttpStatus.ACCEPTED);
        
    }
}
