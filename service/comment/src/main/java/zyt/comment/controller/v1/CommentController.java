package zyt.comment.controller.v1;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zyt.apis.user.IUserClient;
import zyt.comment.dto.Reply;
import zyt.comment.entity.Comment;
import zyt.comment.service.CommentService;
import zyt.comment.dto.CommentDto;
import zyt.common.ResponseResult;
import zyt.user.entity.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private IUserClient iUserClient;

    @PostMapping("/post")
    public ResponseResult<String> postComment(@RequestBody CommentDto comment){

        comment.setCTime(LocalDateTime.now());
        commentService.save(comment);


        return ResponseResult.okResult("发布成功");
    }

    @GetMapping("/{aid}")
    public ResponseResult<List<CommentDto>> getComments(@PathVariable String aid){
        List<CommentDto> dtos=dfs("0",aid);

        return ResponseResult.okResult(dtos);
    }

    public List<CommentDto> dfs(String parentId,String aid){
        LambdaQueryWrapper<Comment> lqw=new LambdaQueryWrapper<>();
        lqw.eq(Comment::getAid,aid).eq(Comment::getParentId,parentId);
        List<Comment> comments = commentService.list(lqw);

        if(comments.size()==0) return null;

        List<CommentDto> dtos=comments.stream().map((item)->{
            CommentDto dto=new CommentDto();
            BeanUtils.copyProperties(item,dto);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");
            String strDate = dtf.format(item.getCTime());
            dto.setCreateTime(strDate);

            User user=iUserClient.getUser(item.getUid()).getData();
            user.setUsername(user.getName());
            dto.setUser(user);
            List<CommentDto> replyComments = dfs(dto.getId(), aid);

            if(replyComments!=null){
                Reply reply=new Reply();
                reply.setTotal(replyComments.size());
                reply.setList(replyComments);
                dto.setReply(reply);
            }else{
                dto.setReply(null);
            }
            return dto;
        }).collect(Collectors.toList());

        return dtos;
    }

}
