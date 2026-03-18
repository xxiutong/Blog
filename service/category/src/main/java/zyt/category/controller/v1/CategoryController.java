package zyt.category.controller.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zyt.apis.blog.IBlogClient;
import zyt.blog.entity.Blog;
import zyt.category.entity.Category;
import zyt.category.service.CategoryService;
import zyt.common.ResponseResult;
import zyt.common.enums.AppHttpCodeEnum;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private IBlogClient iBlogClient;

    @GetMapping("/list")
    public ResponseResult<List<Category>> list(){
        List<Category> list=categoryService.list();
        return ResponseResult.okResult(list);
    }

    @PostMapping("")
    public ResponseResult<Category> add(@RequestBody Category category){
        categoryService.save(category);
        return ResponseResult.okResult(category);
    }

    @DeleteMapping("{id}")
    public ResponseResult<String> del(@PathVariable int id){
        List<Blog> blogs = iBlogClient.getBlogs(id).getData();
        if(blogs==null || blogs.size()==0){
            categoryService.removeById(id);
            return ResponseResult.okResult("删除成功");
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH,"分类中还有文章，无法删除");
        }
    }

}
