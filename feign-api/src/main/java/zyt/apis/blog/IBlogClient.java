package zyt.apis.blog;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zyt.common.ResponseResult;

import java.util.List;

@FeignClient(value = "blogger-blog")
public interface IBlogClient {

    @GetMapping("/api/blog/blogs")
    public ResponseResult<List> getBlogs(@RequestParam("cid") int cid);
}
