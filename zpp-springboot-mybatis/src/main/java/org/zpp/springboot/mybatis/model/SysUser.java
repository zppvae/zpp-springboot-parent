package org.zpp.springboot.mybatis.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.Version;
import lombok.Data;
import org.zpp.springboot.mybatis.converter.SupportConverter;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUser extends Model<SysUser> implements SupportConverter {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("user_id")
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    private String password;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

//    @Version
//    private Integer version;

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
