package com.green.greengram4.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Component
@Getter
public class MyFileUtils {
    private final String uploadPrefixPath; //final붙으면 수정안됨  한번입력되면 값이변경안됨

    public MyFileUtils(@Value("${file.dir}") String uploadPrefixPath) { // @Value
        this.uploadPrefixPath = uploadPrefixPath;
    }

    //폴더 만들기
    public String makeFolders(String path) {
        File folder = new File(uploadPrefixPath, path);
        folder.mkdirs(); //mkdir 폴더1일때 여러개면 오류, mkdirs폴더여러개 가능
        return folder.getAbsolutePath();  // << getAbsolutePath에  uploadPrefixPath, path합쳐진값 들어감
    }

    //랜덤 파일명?만들기
    public String getRandomFileNm() {
        return UUID.randomUUID().toString(); //UUID 범용 고유식별자>>중복되는값이 안나옴
    }

    //확장자 얻어오기
    public String getExt(String fileNm) {
         //subString 문자열 자르기, lastIndexOf()
        return fileNm.substring(fileNm.lastIndexOf("."));
    }

    //랜덤 파일명 만들기 with 확장자
    public String getRandomFileNm(String originFileNm) {
        return getRandomFileNm() + getExt(originFileNm);
    }

    //랜덤 파일명 만들기 with 확장자 from MultipartFile
    public String getRandomFileNm(MultipartFile mf) {
        String fileNm = mf.getOriginalFilename();
        return null;
    }
}
