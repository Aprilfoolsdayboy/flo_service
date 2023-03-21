package com.greenart.flo_service.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Random;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    public String saveImageFile(String type, MultipartFile img) throws Exception {
        // 파일이 저장된 폴더의 경로를 가져와서
        Path targetLocation = Paths.get("/home/flo/images/"+type);
        String fileName = img.getOriginalFilename();
        String[] split = fileName.split("\\."); // .을 기준으로 파일이름을 자르기
        String ext = split[split.length-1]; // 가장 마지막에 위치한 문자열 가져오기(확자자명)
        fileName = generateRandonStr()+"."+ext; // 랜덤으로 생성한 문자열에 확장자 붙이기
        // 입력된 파일명을 추가하여, 저장될 위치를 설정하고
        targetLocation = targetLocation.resolve(fileName); // 파일 경로 표현으로 바꾸기
        // 입력된 파일을 저장된 위치에 복사한다.
        Files.copy(img.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING); // 저장
        // 저장된 위치를 문자열로 변환해서 내어준다.
        return fileName; // 생성된 파일 명 내보내기
    }

    public ResponseEntity<Resource> getImageFile(String type,String filename) throws Exception{
        // Path imgLocation = Paths.get(location).normalize();
        Path imgLocation = Paths.get("/home/flo/images/"+type+"/"+filename).normalize();
        Resource r = new UrlResource(imgLocation.toUri());
        String contentType = "application/octer-stream";
        

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION ,
         "attachment; filename=\""+URLEncoder.encode(r.getFilename(), "UTF-8")).body(r);
    }
    public static String generateRandonStr(){
        int leftLimit = 97;
        int rightLimit = 122;
        int targerStringLength = (int)Math.floor(Math.random()*5)+6; // 6~11자
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit+1)
                .limit(targerStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        Date dt = new Date();
        generatedString +=dt.getTime(); // 현재 시간 값ms정보를 뒤에 붙임
        System.out.println(generatedString);
        return  generatedString;
    }
    public Boolean deleteImageFile(String type, String img)   {
        // 삭제할 파일이 저장된 폴더의 경로를 가져와서
        Path targetLocation = Paths.get("/home/flo/images/"+type);
        targetLocation = targetLocation.resolve(img); // 삭제할 파일의 경로 표현으로 바꾸기
        if(!Files.exists(targetLocation)) return false; //파일이 존재하지 않으면 실패
        try {
        Files.delete(targetLocation); // 파일 삭제 시도
        }
        catch (IOException ioe) {
            return  false; //삭제 실패
        }
        return true; // 삭제 성공
    }
    
}
