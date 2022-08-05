package com.qks.common.vo;

import com.qks.common.po.Job;
import com.qks.common.po.Role;
import lombok.Data;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 23:46
 */
@Data
public class UserInfo {
    private String id;
    private String loginName;
    private String name;
    private String email;
    private String phone;
    private String department;
    private Role[] roles;
    private Job jobs;
}
