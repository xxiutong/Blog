package zyt.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import zyt.user.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
