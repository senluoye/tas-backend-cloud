package com.qks.common.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 17:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends Model{
    private String loginName;
    private String password;
    private String name;
    private String email;
    private BigInteger phone;
    private String department;
}
