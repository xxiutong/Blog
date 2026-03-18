package zyt.comment.listener;


import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import zyt.comment.entity.Comment;
import zyt.comment.service.CommentService;

import java.util.Map;

@Component
public class DelCommentsListener {

    @Autowired
    private CommentService commentService;

    @KafkaListener(topics = {"topic-delcomments"})
    public void onMessage(String message){
        if(StringUtils.isNotBlank(message)){
            Map map = JSON.parseObject(message, Map.class);
            String id=map.get("blogId").toString();
            commentService.remove(new LambdaQueryWrapper<Comment>().eq(Comment::getAid,id));
        }
    }
}
