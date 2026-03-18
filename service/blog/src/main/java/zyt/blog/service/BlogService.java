package zyt.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import zyt.blog.dto.UserSearchDto;
import zyt.blog.entity.Blog;
import zyt.common.ResponseResult;

import java.io.IOException;

public interface BlogService extends IService<Blog> {
    ResponseResult search(String searchWords) throws IOException;
}
