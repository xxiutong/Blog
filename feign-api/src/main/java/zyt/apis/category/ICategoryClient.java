package zyt.apis.category;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zyt.category.entity.Category;
import zyt.common.ResponseResult;

@FeignClient(value = "blogger-category")
public interface ICategoryClient {

    @GetMapping("/api/category/getbyid")
    public ResponseResult<Category> getCategory(@RequestParam("cid") int cid);

}
