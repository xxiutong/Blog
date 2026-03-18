package zyt.files.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilesDto implements Serializable {

    private int errno;

    private Dataurl data;

    private String message;

    public static FilesDto success(String url){
        FilesDto dto=new FilesDto();
        dto.setErrno(0);
        Dataurl dataurl=new Dataurl();
        dataurl.setUrl(url);
        dto.setData(dataurl);

        return dto;
    }

    public static FilesDto error(String msg){
        FilesDto dto=new FilesDto();
        dto.setErrno(1);
        dto.setMessage(msg);
        return dto;
    }

    @Data
    static class Dataurl{
        private String url;
    }
}
