package zyt.apis.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zyt.category.entity.Category;
import zyt.common.ResponseResult;
import zyt.user.entity.User;

@FeignClient(value = "blogger-user")
public interface IUserClient {
    @GetMapping("/api/user/getbyid")
    public ResponseResult<User> getUser(@RequestParam("id") int id);
}
