package org.zpp.springboot.mybatis.model;

import org.zpp.springboot.mybatis.validator.ValidateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author zpp
 * @date 2019/1/25 18:07
 */
public class GroupBean {

    @NotNull(message = "id 不能为空", groups = ValidateGroup.Update.class)
    private Integer id;

    @NotBlank(message = "name 不允许为空", groups = ValidateGroup.Default.class)
    private String name;

    @NotNull(message = "price 不允许为空", groups = ValidateGroup.Default.class)
    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
