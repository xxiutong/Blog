package zyt.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("blog")
public class Blog implements Serializable {
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @TableField("title")
    private String title;

    @TableField("profile")
    private String profile;

    @TableField("content")
    private String content;

    @TableField("attachment")
    private String attachment;

    @TableField("category_id")
    private int categoryId;

    @TableField("publishtime")
    private LocalDateTime publishtime;

    @TableField("status")
    private int status;

    @TableField("user_id")
    private int userId;

}
