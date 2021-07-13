package com.cjw.server.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户登录实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors
@ApiModel(value = "AdminLogin对象",description = " ")
public class AdminLogin {

    @ApiModelProperty(value = "用户名",readOnly = true)
    private String username;
    @ApiModelProperty(value = "密码",readOnly = true)
    private String password;
}
