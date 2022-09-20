package com.qks.common.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 18:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Model {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(fill = FieldFill.INSERT)
    private Timestamp createAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Timestamp updateAt;

    @TableLogic
    private int deleteAt;
}
