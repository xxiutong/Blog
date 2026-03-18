package zyt.comment.dto;

import lombok.Data;
import zyt.comment.entity.Comment;

import java.util.List;

@Data
public class Reply{
    private int total;
    private List<CommentDto> list;
}

