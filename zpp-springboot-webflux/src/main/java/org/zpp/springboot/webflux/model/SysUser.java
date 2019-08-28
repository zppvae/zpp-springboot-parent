package org.zpp.springboot.webflux.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zpp
 * @date 2019/8/27 16:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class SysUser {

    @Id
    private String userId;

    private String username;

    private String password;

    private int age;

    @Indexed(unique = true)
    private String email;
}
