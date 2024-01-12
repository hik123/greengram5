package com.green.greengram4.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@Import({ MyFileUtils.class })
@TestPropertySource(properties = {
        "file.dir=D:/home/download",
})
public class MyFileUtilsTest {

    @Autowired
    private MyFileUtils myFileUtils;

    @Test
    public void makeFolderTest() {
        String path = "/ggg"; // 파일인지 폴더인지 상관없이 파일이 존재하는지
        File preFolder = new File(myFileUtils.getUploadPrefixPath(), path); //경로 ()문자열 합쳐짐
        assertEquals(false, preFolder.exists()); //파일이 존재하는지   존재하면true

        String newPath = myFileUtils.makeFolders(path);
        File newFolder = new File(newPath);
        assertEquals(preFolder.getAbsolutePath(), newFolder.getAbsolutePath());
        assertEquals(true, newFolder.exists());
    }

    @Test
    public void getRandomFileNmTest() {
        String fileNm = myFileUtils.getRandomFileNm();
        System.out.println("fileNm: " + fileNm);
        assertNotNull(fileNm);
        assertNotEquals("", fileNm);
    }

    @Test
    public void getExtText() {
        String fileNm = "abc.efg.eee.jpg";
        String ext = myFileUtils.getExt(fileNm);
        assertEquals(".jpg", ext);

        String fileNm2 = "jjj-afddasf.pnge";
        String ext2 = myFileUtils.getExt(fileNm2);
        assertEquals(".pnge", ext2);
    }

    @Test
    public void getRandomFileNm2() {
        String fileNm1 = "반갑다 친구야.jpeg";
        String rFileNm1 = myFileUtils.getRandomFileNm(fileNm1);
        System.out.println("rFileNm1 : " + rFileNm1);
        //랜덤문자열.jpeg

        String fileNm2 = "asfsafsda.qq";
        String rFileNm2 = myFileUtils.getRandomFileNm(fileNm2);
        System.out.println("rFileNm2 : " + rFileNm2);
        //랜덤문자열.qq
    }


}
