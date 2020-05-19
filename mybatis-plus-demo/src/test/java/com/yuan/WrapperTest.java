package com.yuan;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuan.mapper.UserMapper;
import com.yuan.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WrapperTest {

    @Autowired
    UserMapper userMapper;

    /**
     * 查询name不为空的用户，并且邮箱不为空的用户，年龄大于等于30
     */
    @Test
    public void test1() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .isNotNull("name")
                .isNotNull("email")
                .ge("age", 90);
        userMapper.selectList(wrapper).forEach(System.out::println);

    }


    @Test
    public void testMap() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yuan");
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }

    /**
     * 测试区间
     */
    @Test
    public void testBetween() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age", 18, 25);
        userMapper.selectList(wrapper).forEach(System.out::println);
    }

    /**
     * 测试模糊查询
     * email中含有dou
     * 名字J%
     */
    @Test
    public void testLike() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .like("email", "dou")
                .likeLeft("name", "J");
        userMapper.selectList(wrapper).forEach(System.out::println);
    }

    @Test
    public void testSQL() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.inSql("id", "select id from user where id <5");
        userMapper.selectList(wrapper).forEach(System.out::println);
    }


    @Test
    public void testOrderBy() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        userMapper.selectList(wrapper).forEach(System.out::println);
    }

}
