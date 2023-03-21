package com.greenart.flo_service.api;

import com.greenart.flo_service.vo.album.AlbumInsertVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/album")
@RequiredArgsConstructor
public class AlbumAPIController {
    @PutMapping("")
    public ResponseEntity<Object> putAlbumInfo(AlbumInsertVO data) {

        return new ResponseEntity<>(null, HttpStatus.OK);

    }
}
