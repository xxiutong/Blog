package zyt.comment.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@TableName("comment")
public class Comment implements Serializable {

    @TableField("id")
    private String id;
    @TableField("parentId")
    private String parentId;

    @TableField("content")
    private String content;
    @TableField("likes")
    private int likes;

    @TableField("create_time")
    private LocalDateTime cTime;
    @TableField("uid")
    private Integer uid;

    @TableField("aid")
    private String aid;

}
