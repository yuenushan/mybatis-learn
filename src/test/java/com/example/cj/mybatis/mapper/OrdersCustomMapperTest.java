package com.example.cj.mybatis.mapper;

import com.example.cj.mybatis.po.Orders;
import com.example.cj.mybatis.po.OrdersCustom;
import com.example.cj.mybatis.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

public class OrdersCustomMapperTest {

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
    public void testFindOrdersUser() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 创建代理对象
        OrdersCustomMapper ordersCustomMapper = sqlSession
                .getMapper(OrdersCustomMapper.class);
        List<OrdersCustom> ordersUser = ordersCustomMapper.findOrdersUser();
        System.out.println(ordersUser);
        sqlSession.close();
    }

    @Test
    public void testFindOrdersUserResultMap() throws Exception {

        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 创建代理对象
        OrdersCustomMapper ordersCustomMapper = sqlSession
                .getMapper(OrdersCustomMapper.class);

        // 调用maper的方法
        List<Orders> list = ordersCustomMapper.findOrdersUserResultMap();

        System.out.println(list);

        sqlSession.close();
    }

    @Test
    public void testFindOrdersAndOrderDetailResultMap() throws Exception {

        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 创建代理对象
        OrdersCustomMapper ordersCustomMapper = sqlSession
                .getMapper(OrdersCustomMapper.class);

        // 调用maper的方法
        List<Orders> list = ordersCustomMapper.findOrdersAndOrderDetailResultMap();

        list.forEach(System.out::println);

        sqlSession.close();
    }

    @Test
    public void testFindUserAndItemsResultMap() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 创建代理对象
        OrdersCustomMapper ordersCustomMapper = sqlSession
                .getMapper(OrdersCustomMapper.class);

        // 调用maper的方法
        List<User> list = ordersCustomMapper.findUserAndItemsResultMap();

        list.forEach(System.out::println);

        sqlSession.close();
    }

    // 查询订单关联查询用户，用户信息使用延迟加载
    @Test
    public void testFindOrdersUserLazyLoading() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();// 创建代理对象
        OrdersCustomMapper ordersMapperCustom = sqlSession
                .getMapper(OrdersCustomMapper.class);
        // 查询订单信息（单表）
        List<Orders> list = ordersMapperCustom.findOrdersUserLazyLoading();
        // 遍历上边的订单列表
        for (Orders orders : list) {
            // 执行getUser()去查询用户信息，这里实现按需加载
            System.out.println(orders);
            User user = orders.getUser();
            System.out.println(user);
        }
    }


}