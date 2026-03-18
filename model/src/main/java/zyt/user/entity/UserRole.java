package zyt.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("user_role")
public class UserRole implements Serializable {
    @TableField("user_id")
    int userId;
    @TableField("role_id")
    int roleId;

}
