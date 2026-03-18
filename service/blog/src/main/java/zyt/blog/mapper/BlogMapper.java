package zyt.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import zyt.blog.entity.Blog;

@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
}
