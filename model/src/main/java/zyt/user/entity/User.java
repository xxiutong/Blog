package zyt.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("phone")
    private String phone;
    @TableField("avatar")
    private String avatar;
    @TableField("email")
    private String email;
    @TableField("password")
    private String password;
    @TableField("name")
    private String name;
    @TableField("motto")
    private String motto;
    @TableField("status")
    private int status;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String role;
}
