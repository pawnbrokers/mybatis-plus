package com.yuan.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.mybatisplus.mapper.UserMapper;
import com.yuan.mybatisplus.pojo.User;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class MybatisplusApplicationTests {

    @Autowired//继承了BaseMapper，所有的方法都来自父类，我们也可以自己编写接口
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        //参数是一个Wrapper，条件构造器，我们可以先写null
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);

    }

    @Test
    public void testInsert(){
        User user = new User();
        user.setName("yuan");
        user.setAge(296);
        user.setEmail("yuantb@yeah.net");

        int insert = userMapper.insert(user);
        System.out.println(insert);//id会自动回填
    }

    //测试更新
    @Test
    public void testUpdate(){
        User user = new User();
        user.setName("yuan");
        user.setAge(456);
        user.setEmail("yuantb@yeah.net");
        user.setId(6L);
        int insert = userMapper.updateById(user);
        userMapper.updateById(user);
    }


    //测试乐观锁
    @Test
    public void testLock(){
        //1. 查询用户信息
        User user = userMapper.selectById(1L);
        //2.修改用户信息
        user.setName("yuantangbo");
        user.setEmail("1073617063@qq.com");

        //3.执行更新
        userMapper.updateById(user);

    }

    //测试乐观锁
    @Test
    public void testLock2(){
        //线程1
        //1. 查询用户信息
        User user = userMapper.selectById(1L);
        //2.修改用户信息
        user.setName("111");
        user.setEmail("1073617063@qq.com");

        //模拟另外一个线程执行插队
        User user1 = userMapper.selectById(1L);
        user1.setName("222");
        user1.setEmail("1073617063@qq.com");
        userMapper.updateById(user1);

        //3.执行更新
        //自旋锁来多次尝试提交
        userMapper.updateById(user);//如果没有乐观锁，会覆盖user1的值

    }

    @Test
    public void testSelect(){
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    @Test//批量查询
    public void testSelect2(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
users.forEach(System.out::println);

    }

    @Test//条件查询,通过map
    public void testSelectByMap(){
        HashMap<String, Object> map = new HashMap<>();
        Object put = map.put("name", "yuan");
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    //测试分页查询
    @Test
    public void testPage(){
        //参数一，当前页，参数二，页面大小
        Page<User> objectPage = new Page<>(2,5);
        userMapper.selectPage(objectPage,null);
        objectPage.getRecords().forEach(System.out::println);
    }

    //测试删除
    @Test
    public void testDelete(){
        userMapper.deleteById(1L);
    }

    //批量删除
    @Test//还可以再测试逻辑删除
    public void testDeleteBatch(){
        userMapper.deleteBatchIds(Arrays.asList(0,126236896493200998L));
    }


}
