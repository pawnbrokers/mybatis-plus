package com.yuan.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuan.mybatisplus.mapper.UserMapper;
import com.yuan.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class WrapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads(){
        //查询name不为空，email不为空，且年龄大于12
    QueryWrapper<User> wrapper =  new QueryWrapper<User>();
    wrapper.isNotNull("name")
            .isNotNull("email")
            .ge("age",12);
        List<User> users = userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }
}
