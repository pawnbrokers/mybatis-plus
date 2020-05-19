package com.yuan.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author yuan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    //对应数据库中的主键(uuid,自增id，雪花算法（分布式）)
    //默认的方案是全局唯一id
    @TableId(type = IdType.ID_WORKER)
    private Long id;
    private String name;
    private int age;
    private String email;

    //字段添加填充内容
    @TableField(fill = FieldFill.INSERT)
    private Date create_time;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date update_time;

    @Version//代表乐观锁注解
    private int version;

    //逻辑删除
    @TableLogic
    private int deleted;

}
