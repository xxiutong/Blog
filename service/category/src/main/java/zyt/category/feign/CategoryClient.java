package zyt.category.feign;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zyt.apis.category.ICategoryClient;
import zyt.category.entity.Category;
import zyt.category.service.CategoryService;
import zyt.common.ResponseResult;

@RestController
public class CategoryClient implements ICategoryClient {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/api/category/getbyid")
    public ResponseResult<Category> getCategory(@RequestParam("cid") int cid) {

        Category category = categoryService.getById(cid);
        return ResponseResult.okResult(category);

    }


}
