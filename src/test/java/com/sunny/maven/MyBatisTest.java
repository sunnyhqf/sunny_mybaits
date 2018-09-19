package com.sunny.maven;

import com.sunny.maven.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class MyBatisTest {

    private SqlSession getSession() throws IOException {
        // 从classpath目录加载配置文件
        InputStream ips = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 获取Builder
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 根据ips配置构建sessionFactory
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(ips);
        // sessionFactory打开一个会话， 进行数据库连接操作
        SqlSession session = sqlSessionFactory.openSession();
        return session;
    }

    @Test
    public void testSelect() throws IOException {
        SqlSession sqlSession = this.getSession();
        // 通过namespace 和 id 调用select SQL语句，设置占位参数，获取查询结果
        User user = sqlSession.selectOne("com.sunny.maven.pojo.User.selectUserById", 22);
        System.out.println(user.getId() + " : " + user.getUsername() + " : " + user.getAddress());
        // 释放资源
        sqlSession.close();
    }

    @Test
    public void testSelectByUsername() throws IOException {
        SqlSession sqlSession = this.getSession();
        List<User> users = sqlSession.selectList("com.sunny.maven.pojo.User.selectUserByUsername", "小");
        for (User user : users) {
            System.out.println(user.getId() + " : " + user.getUsername() + " : " + user.getAddress());
        }
        sqlSession.close();
    }

    @Test
    public void testSelectByUsername2() throws IOException, ParseException {
        SqlSession session = this.getSession();
        User user = new User();
        user.setUsername("qq");
        user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("2008-09-19"));
        user.setSex("s");
        user.setAddress("深圳");
        session.insert("com.sunny.maven.pojo.User.insertUser", user);
        System.err.println("user ID = " + user.getId());
        session.commit();
        session.close();
    }



}
