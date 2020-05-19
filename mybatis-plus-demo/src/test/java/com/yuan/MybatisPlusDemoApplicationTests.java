package com.yuan;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.mapper.UserMapper;
import com.yuan.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MybatisPlusDemoApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {

        User user = userMapper.selectById(1L);
        System.out.println(user);

    }

    @Test
    public void testInsert() {
        User user = new User();
        user

                .setName("bobo")
                .setAge(25)
                .setEmail("yuantb@yeah.net");
        int insert = userMapper.insert(user);
    }


    @Test
    public void testUpdate() {
        User user = new User();
        user
                .setId(8L)
                .setAge(998)
                .setName("bobobobobo");
        int i = userMapper.updateById(user);
        System.out.println(i);
    }


    @Test
    public void testOptimisticLocker() {
        User user = userMapper.selectById(8L);
        user
                .setName("faker")
                .setAge(23)
                .setEmail("skt@skt.com");
        userMapper.updateById(user);
    }

    @Test
    public void testOptimisticLocker2() {
        User user = userMapper.selectById(8L);
        user
                .setName("faker")
                .setAge(23)
                .setEmail("skt@skt.com");
        //此时还未进行修改，却被其他线程插入
        User user1 = userMapper.selectById(8L);
        user1
                .setName("faker111")
                .setAge(111)
                .setEmail("111111@skt.com");
        //此时先执行了后续线程的更新操作
        userMapper.updateById(user1);

        userMapper.updateById(user);
    }


    @Test
    public void testSelectById() {
        User user = userMapper.selectById(8L);
        System.out.println(user);
    }

    @Test
    public void testSelectAll() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }


    @Test
    public void tsetSelectByBatchId() {
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 3, 5, 8));
        users.forEach(System.out::println);
    }

    @Test
    public void testSelectByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "yuan");
        map.put("age", 25);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }


    @Test
    public void testPage() {
        /**
         * 参数1： 当前是第一页
         * 参数2：一页显示多少条记录
         */
        Page<User> page = new Page<>(2, 5);
        userMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);
        System.out.println(page.getTotal());
    }


    @Test
    public void testDeleteById(){
        int i = userMapper.deleteById(8L);
        System.out.println(i);
    }
}
