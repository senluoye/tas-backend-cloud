package com.qks.common.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 18:27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Task extends Model{
    private String name;
    private String description;
    private Task[] subTasks;
    private User[] ownUsers;
}
