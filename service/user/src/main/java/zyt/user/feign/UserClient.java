package zyt.user.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import zyt.apis.user.IUserClient;
import zyt.category.entity.Category;
import zyt.common.ResponseResult;
import zyt.user.entity.User;
import zyt.user.service.UserService;

@RestController
public class UserClient implements IUserClient {

    @Autowired
    private UserService userService;

    @GetMapping("/api/user/getbyid")
    @Override
    public ResponseResult<User> getUser(int id) {
        return ResponseResult.okResult(userService.getById(id));
    }
}
