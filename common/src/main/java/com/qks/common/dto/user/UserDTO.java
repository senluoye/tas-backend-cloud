package com.qks.common.dto.user;

import com.qks.common.po.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-15 20:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends Model {
    private String loginName;
    private String password;
    private String name;
    private String email;
    private BigInteger phone;
    private String department;
}
