package org.zpp.springboot.mybatis.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author zpp
 * @date 2019/1/25 14:30
 */
@Builder
@ToString
public class R implements Serializable {

    @Getter
    @Setter
    private int code = 0;

    @Getter
    @Setter
    private String msg;

    public R(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
