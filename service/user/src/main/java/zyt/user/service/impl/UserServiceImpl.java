package zyt.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import zyt.user.mapper.UserMapper;
import zyt.user.service.UserService;
import org.springframework.stereotype.Service;
import zyt.user.entity.User;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
