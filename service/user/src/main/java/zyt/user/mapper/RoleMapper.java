package zyt.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import zyt.user.entity.UserRole;

@Mapper
public interface RoleMapper extends BaseMapper<UserRole> {
}
