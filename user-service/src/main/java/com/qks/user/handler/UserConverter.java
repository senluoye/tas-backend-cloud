package com.qks.user.handler;

import com.qks.common.dto.user.UserDTO;
import com.qks.common.po.User;

/**
 * @ClassName UserConverter
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-09-20 10:59
 */
public class UserConverter {
    public static User converterToUser(UserDTO userDTO) {
        return User.builder()
                .loginName(userDTO.getLoginName())
                .password(userDTO.getPassword())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .department(userDTO.getDepartment())
                .build();
    }
}
