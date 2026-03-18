package zyt.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import zyt.comment.entity.Comment;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
