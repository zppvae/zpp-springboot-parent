package org.zpp.springboot.sharding.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Builder
@Data
@TableName("t_user")
@EqualsAndHashCode(callSuper = false)
public class User extends Model<User> {

    @TableId("id")
    private int id;

    private String username;

    private String password;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}