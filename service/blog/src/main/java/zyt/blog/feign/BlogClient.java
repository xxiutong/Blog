package zyt.blog.feign;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import zyt.apis.blog.IBlogClient;
import zyt.blog.entity.Blog;
import zyt.blog.service.BlogService;
import zyt.common.ResponseResult;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class BlogClient implements IBlogClient {
    @Autowired
    private BlogService blogService;

    @Override
    @GetMapping("/api/blog/blogs")
    public ResponseResult<List> getBlogs(@PathParam("cid") int cid) {

        List<Blog> list = blogService.list(new LambdaQueryWrapper<Blog>().eq(Blog::getCategoryId, cid));
        return ResponseResult.okResult(list);
    }
}
