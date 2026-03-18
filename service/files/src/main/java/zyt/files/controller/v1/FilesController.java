package zyt.files.controller.v1;


import com.heima.file.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import zyt.files.dto.FilesDto;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/files")
public class FilesController {


    @Autowired
    private FileStorageService fileStorageService;


    @PostMapping("/upload_picture")
    public FilesDto uploadPicture(MultipartFile multipartFile){

        //1.检查参数
        if(multipartFile == null || multipartFile.getSize() == 0){
            return FilesDto.error("传值为空!");
        }

        //2.上传图片到minIO中
        String fileName = UUID.randomUUID().toString().replace("-", "");
        //aa.jpg
        String originalFilename = multipartFile.getOriginalFilename();
        String postfix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileId = null;
        try {
            fileId = fileStorageService.uploadImgFile("", fileName + postfix, multipartFile.getInputStream());
            log.info("上传图片到MinIO中，fileId:{}",fileId);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("WmMaterialServiceImpl-上传文件失败");

            return FilesDto.error("上传失败");
        }

        return FilesDto.success(fileId);

    }

    @PostMapping("/upload_video")
    public FilesDto uploadVideo(MultipartFile multipartFile){

        //1.检查参数
        if(multipartFile == null || multipartFile.getSize() == 0){
            return FilesDto.error("传值为空!");
        }

        //2.上传图片到minIO中
        String fileName = UUID.randomUUID().toString().replace("-", "");
        //aa.jpg
        String originalFilename = multipartFile.getOriginalFilename();
        String postfix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileId = null;
        try {
            fileId = fileStorageService.uploadVideoFile("", fileName + postfix, multipartFile.getInputStream());
            log.info("上传视频到MinIO中，fileId:{}",fileId);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("WmMaterialServiceImpl-上传文件失败");

            return FilesDto.error("上传失败");
        }

        return FilesDto.success(fileId);

    }

    @PostMapping("/upload_zip")
    public FilesDto uploadZip(MultipartFile file){

        //1.检查参数
        if(file == null || file.getSize() == 0){
            return FilesDto.error("传值为空!");
        }

        //2.上传图片到minIO中
        String fileName = UUID.randomUUID().toString().replace("-", "");
        //aa.jpg
        String originalFilename = file.getOriginalFilename();
        String postfix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileId = null;
        try {
            fileId = fileStorageService.uploadZipFile("", fileName + postfix, file.getInputStream());
            log.info("上传附件Zip到MinIO中，fileId:{}",fileId);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("WmMaterialServiceImpl-上传文件失败");

            return FilesDto.error("上传失败");
        }

        return FilesDto.success(fileId);

    }
}
