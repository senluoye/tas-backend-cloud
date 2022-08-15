package com.qks.common.bo;

import com.qks.common.po.Model;
import com.qks.common.po.Task;
import com.qks.common.po.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-13 17:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TaskTemp extends Model {
    private String name;
    private String description;
    private List<Task> subTasks;
    private User[] ownUsers;
}
