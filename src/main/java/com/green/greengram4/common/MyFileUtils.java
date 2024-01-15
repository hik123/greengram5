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
        String path2 = folder.getAbsolutePath();
        return path2;  // << getAbsolutePath에  uploadPrefixPath, path합쳐진값 들어감
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
        //String fileNm = mf.getOriginalFilename();
        return getRandomFileNm(mf.getOriginalFilename());
    }

    //메모리에 있는 내용 >  파일로 옮기는 메소드
    public String transferTo(MultipartFile mf, String target) {
        String fileNm = getRandomFileNm(mf); // 확장자 때문에 mf보냄
        String folderPath = makeFolders(target);  //폴더만들고
        File saveFile = new File(folderPath, fileNm);  // 파일 객체 만듬
        //saveFile.exists(); // 호출하면 boolean , 파일 존재하면true , 없으면 false
        try {
            mf.transferTo(saveFile); // transferTo에 파일객체 보내면 메모리에 있던 내용 파일 옮겨줌
            return fileNm; // 랜덤한 파일명 넘겨줌 // 보통 경로는 저장안하고 파일명만 db에 저장? 저장해도 상관없긴하지만 경로바뀌더라도 db는 안건드려도되서
                                // 절대값으로 지정하면안됨 //파일경로 다적음 <<절대경로
                                // D:\home\download가 시작점일때 시작점 부터(이후)의 경로 >> 상대경로
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
