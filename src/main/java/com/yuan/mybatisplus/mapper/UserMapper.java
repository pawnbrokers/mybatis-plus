package com.yuan.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuan.mybatisplus.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author yuan
 */

//在对应的mapper上面实现基本的接口即可BaseMapper
@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

    //所有的CURD已经完成
    //不需要再配置xml和mybatis.xml等文件，只需要再加一个注解
}
