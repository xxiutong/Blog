package zyt.blog.controller.v1;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Delete;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import zyt.apis.category.ICategoryClient;
import zyt.apis.user.IUserClient;
import zyt.blog.dto.BlogDto;
import zyt.blog.entity.Blog;
import zyt.blog.service.BlogService;
import zyt.blog.vos.BlogVo;
import zyt.category.entity.Category;
import zyt.common.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private ICategoryClient iCategoryClient;

    @Autowired
    private IUserClient iUserClient;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/detail")
    public ResponseResult<BlogDto> detail(String id){
        Blog item = blogService.getById(id);
        BlogDto dto=new BlogDto();
        BeanUtils.copyProperties(item,dto);
        Category category = iCategoryClient.getCategory(item.getCategoryId()).getData();
        dto.setCategory(category);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");
        String strDate = dtf.format(item.getPublishtime());
        dto.setFormatTime(strDate);
        dto.setAuthor(iUserClient.getUser(item.getUserId()).getData());
        return ResponseResult.okResult(dto);
    }



    @PostMapping("/sub")
    public ResponseResult<String> sub(@RequestBody Blog blog){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request1 = attributes.getRequest();
        String token=request1.getHeader("token");


        if(StringUtils.isNotEmpty(blog.getId())){
            Blog one = blogService.getById(blog.getId());
            blog.setStatus(one.getStatus());
            blog.setUserId(one.getUserId());
            blogService.updateById(blog);
        }else{
            blog.setUserId(Integer.parseInt(redisTemplate.opsForValue().get(token).toString()));
            blog.setPublishtime(LocalDateTime.now());
            blog.setStatus(1);
            blogService.save(blog);
            createBlogEsIndex(blog);
        }

        return ResponseResult.okResult("提交成功");
    }

    public void createBlogEsIndex(Blog blog){
        BlogVo vo=new BlogVo();
        BeanUtils.copyProperties(blog,vo);
        vo.setCategoryName(iCategoryClient.getCategory(blog.getCategoryId()).getData().getName());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");
        String strDate = dtf.format(blog.getPublishtime());
        vo.setFormatTime(strDate);

        String voJson=JSON.toJSONString(vo);

        IndexRequest indexRequest = new IndexRequest("blogger_blogs");
        indexRequest.id(vo.getId());
        indexRequest.source(voJson, XContentType.JSON);
        try {
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("sync es error={}",e);
        }

    }

    @GetMapping("/page")
    public ResponseResult<Page> page(int page,int pageSize,String query,Integer categoryId){
        Page<Blog> pageInfo=new Page<>(page,pageSize);
        blogService.page(pageInfo,new LambdaQueryWrapper<Blog>().eq(Blog::getStatus,1).like(StringUtils.isNotEmpty(query),Blog::getTitle,query).eq(Objects.nonNull(categoryId),Blog::getCategoryId,categoryId));
        Page<BlogDto> dtoPage=new Page<>(page,pageSize);
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Blog> records =pageInfo.getRecords();
        List<BlogDto> dtoRecords=records.stream().map((item)->{
            BlogDto dto=new BlogDto();
            BeanUtils.copyProperties(item,dto);
            Category category = iCategoryClient.getCategory(item.getCategoryId()).getData();
            dto.setCategory(category);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");
            String strDate = dtf.format(item.getPublishtime());
            dto.setFormatTime(strDate);
            return dto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(dtoRecords);
        return ResponseResult.okResult(dtoPage);
    }

    @DeleteMapping("/{id}")
    public ResponseResult<String> del(@PathVariable String id){
        boolean res = blogService.removeById(id);
        if(res){
            Map<String,Object> map=new HashMap<>();
            map.put("blogId",id);
            kafkaTemplate.send("topic-delcomments", JSON.toJSONString(map));
        }
        return ResponseResult.okResult("删除成功");
    }

    @GetMapping("/search")
    public ResponseResult search(String query) throws IOException {
        return blogService.search(query);
    }
}
