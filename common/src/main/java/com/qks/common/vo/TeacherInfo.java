package com.qks.common.vo;

import com.qks.common.po.Evaluation;
import lombok.Builder;
import lombok.Data;
import org.bouncycastle.util.Times;

import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-08 20:20
 */
@Data
@Builder
public class TeacherInfo {
    private Integer userJobRelationId;
    private Integer jobId;
    private Integer userId;
    private String userName;
    private Timestamp createAt;
    private String type;
    private List<MyEvaluation> evaluations;
}
