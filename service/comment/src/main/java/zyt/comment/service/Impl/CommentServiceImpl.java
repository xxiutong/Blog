package zyt.comment.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import zyt.comment.mapper.CommentMapper;
import org.springframework.stereotype.Service;
import zyt.comment.service.CommentService;
import zyt.comment.entity.Comment;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
}
