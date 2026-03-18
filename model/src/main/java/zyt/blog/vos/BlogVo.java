package zyt.blog.vos;

import lombok.Data;

import java.io.Serializable;

@Data
public class BlogVo implements Serializable {

    String id;
    String formatTime;
    String categoryName;
    String profile;
    String title;
    String content;


}
