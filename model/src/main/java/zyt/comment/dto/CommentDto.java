package zyt.comment.dto;

import lombok.Data;
import zyt.comment.entity.Comment;
import zyt.user.entity.User;

import java.io.Serializable;
import java.util.List;

@Data
public class CommentDto extends Comment implements Serializable {
    private User user;
    private Reply reply;
    private String createTime;
}

