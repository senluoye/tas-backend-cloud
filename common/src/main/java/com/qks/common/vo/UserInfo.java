package com.qks.common.vo;

import com.qks.common.po.Job;
import com.qks.common.po.Role;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 23:46
 */
@Data
@Builder
public class UserInfo {
    private Integer id;
    private String loginName;
    private String name;
    private String email;
    private BigInteger phone;
    private String department;
    private List<Role> roles;
    private List<Job> jobs;
}
