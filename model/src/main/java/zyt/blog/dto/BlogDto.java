package zyt.blog.dto;

import lombok.Data;
import zyt.blog.entity.Blog;
import zyt.category.entity.Category;
import zyt.user.entity.User;

@Data
public class BlogDto extends Blog {

    private Category category;

    private String formatTime;

    private User author;
}
