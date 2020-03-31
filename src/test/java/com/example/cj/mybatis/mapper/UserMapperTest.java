package com.example.cj.mybatis.mapper;

import com.example.cj.mybatis.po.User;
import com.example.cj.mybatis.po.UserCustom;
import com.example.cj.mybatis.po.UserQueryVo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UserMapperTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp() throws Exception {
        //创建SqlSessionFactory
        String resource="SqlMapConfig.xml";

        //将配置文件加载成流
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //创建会话工厂，传入mybatis配置文件的信息
        sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testFindUserById() throws Exception{

        SqlSession sqlSession=sqlSessionFactory.openSession();

        //创建UserMapper代理对象
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);

        //调用userMapper的方法
        User user=userMapper.findUserById(1);

        System.out.println(user.getUsername());
    }

    //用户信息的综合 查询
    @Test
    public void testFindUserList() throws Exception {

        SqlSession sqlSession = sqlSessionFactory.openSession();

        //创建UserMapper对象，mybatis自动生成mapper代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //创建包装对象，设置查询条件
        UserQueryVo userQueryVo = new UserQueryVo();
        userQueryVo.setIds(Arrays.asList(10, 11));
        UserCustom userCustom = new UserCustom();
        //由于这里使用动态sql，如果不设置某个值，条件不会拼接在sql中
        userCustom.setSex("1");
        userCustom.setUsername("张三");
        userQueryVo.setUserCustom(userCustom);
        //调用userMapper的方法

        List<UserCustom> list = userMapper.findUserList(userQueryVo);

        System.out.println(list);


    }

    //用户信息综合查询总数
    @Test
    public void testFindUserCount() throws Exception {

        SqlSession sqlSession = sqlSessionFactory.openSession();

        //创建UserMapper对象，mybatis自动生成mapper代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //创建包装对象，设置查询条件
        UserQueryVo userQueryVo = new UserQueryVo();
        UserCustom userCustom = new UserCustom();
        //由于这里使用动态sql，如果不设置某个值，条件不会拼接在sql中
        userCustom.setSex("1");
        userCustom.setUsername("小");
        userQueryVo.setUserCustom(userCustom);
        //调用userMapper的方法

        int count = userMapper.findUserCount(userQueryVo);

        System.out.println(count);
    }

    @Test
    public void testFindUserByIdResultMap() throws Exception {

        SqlSession sqlSession = sqlSessionFactory.openSession();

        //创建UserMapper对象，mybatis自动生成mapper代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //调用userMapper的方法

        User user = userMapper.findUserByIdResultMap(1);

        System.out.println(user);


    }

    @Test
    public void testCache1() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();// 创建代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        // 下边查询使用一个SqlSession
        // 第一次发起请求，查询id为1的用户
        User user1 = userMapper.findUserById(1);
        System.out.println(user1);

        // 如果sqlSession去执行commit操作（执行插入、更新、删除），清空SqlSession中的一级缓存，这样做的目的为了让缓存中存储的是最新的信息，避免脏读。

        // 更新user1的信息
        // user1.setUsername("测试用户22");
        // userMapper.updateUser(user1);
        // //执行commit操作去清空缓存
        // sqlSession.commit();
        User user10= userMapper.findUserById(10);
        user10.setSex("2");
        userMapper.updateUser(user10);

        // 第二次发起请求，查询id为1的用户
        User user2 = userMapper.findUserById(1);
        System.out.println(user2);

        sqlSession.close();

    }

    // 二级缓存测试
    @Test
    public void testCache2() throws Exception {
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        SqlSession sqlSession3 = sqlSessionFactory.openSession();
        // 创建代理对象
        UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
        // 第一次发起请求，查询id为1的用户
        User user1 = userMapper1.findUserById(1);
        System.out.println(user1);

        //这里执行关闭操作，将sqlsession中的数据写到二级缓存区域
        sqlSession1.close();


		//使用sqlSession3执行commit()操作
		UserMapper userMapper3 = sqlSession3.getMapper(UserMapper.class);
		User user  = userMapper3.findUserById(1);
		user.setUsername("张明明");
		userMapper3.updateUser(user);
		//执行提交，清空UserMapper下边的二级缓存
		sqlSession3.commit();
		sqlSession3.close();



        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
        // 第二次发起请求，查询id为1的用户
        User user2 = userMapper2.findUserById(1);
        System.out.println(user2);

        sqlSession2.close();
    }

}