package zyt.user.controller.v1;

//import com.alibaba.nacos.common.utils.MapUtils;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import utils.common.AppJwtUtil;
import zyt.common.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import zyt.common.ResponseResult;
import zyt.user.entity.User;
import zyt.user.entity.UserRole;
import zyt.user.service.RoleService;
import zyt.user.service.UserService;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserLoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RoleService roleService;


    @PostMapping("/back/login")
    public ResponseResult<Map> backLogin(@RequestBody User user){
        User one = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getPhone, user.getPhone()));
        LambdaQueryWrapper<UserRole> lqw=new LambdaQueryWrapper<>();
        lqw.eq(UserRole::getUserId,one.getId());
        UserRole role = roleService.getOne(lqw);
        if(role.getRoleId()!=1){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"非管理员账户");
        }
        one.setRole("host");
        if(one==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"用户不存在");
        }
        String pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        if(!pwd.equals(one.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR,"用户密码错误");
        }

        String token= AppJwtUtil.getToken(one.getId().longValue());
        redisTemplate.opsForValue().set(token,String.valueOf(one.getId()));
        one.setPassword("");
        return ResponseResult.okResult(MapUtil.builder().put("user",one).put("token",token).build());
    }

    @PostMapping("/login")
    public ResponseResult<Map> login(@RequestBody User user){
        User one = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getPhone, user.getPhone()));
//        LambdaQueryWrapper<UserRole> lqw=new LambdaQueryWrapper<>();
//        lqw.eq(UserRole::getUserId,one.getId());
//        UserRole role = roleService.getOne(lqw);
//        if(role.getRoleId()!=1){
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"非管理员账户");
//        }
        if(one==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"用户不存在");
        }
        String pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        if(!pwd.equals(one.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR,"用户密码错误");
        }

        one.setRole("visitor");

        String token= AppJwtUtil.getToken(one.getId().longValue());
        redisTemplate.opsForValue().set(token,String.valueOf(one.getId()));
        one.setPassword("");
        return ResponseResult.okResult(MapUtil.builder().put("user",one).put("token",token).build());
    }

    @GetMapping("/host")
    public ResponseResult<User> getHost(){
        User host = userService.getById(1);
        return ResponseResult.okResult(host);
    }


}
