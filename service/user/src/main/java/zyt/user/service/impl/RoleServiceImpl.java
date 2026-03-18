package zyt.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zyt.user.entity.UserRole;
import zyt.user.mapper.RoleMapper;
import zyt.user.service.RoleService;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, UserRole> implements RoleService {
}
