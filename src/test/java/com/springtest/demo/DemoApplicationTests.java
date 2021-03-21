package com.springtest.demo;

import com.springtest.demo.entities.Blog;
import com.springtest.demo.mapper.BlogMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

@SpringBootTest
class DemoApplicationTests {

    public static void main(String[] args) {
        contextLoads();
    }

     static void contextLoads() {
        DataSource dataSource = getBlogDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(BlogMapper.class);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        Blog blog =sqlSession.selectOne("com.springtest.demo.mapper.BlogMapper.selectBlog",1);
        System.out.println(blog);
    }

    private static DataSource getBlogDataSource() {
        PooledDataSource dataSource=new PooledDataSource();
        dataSource.setDriver("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://8.129.178.38:3306/redis_test?" +
                "useUnicode=true&characterEncoding=utf-8&useSSL=true" +
                "&serverTimezone=Asia/Shanghai&useAffectedRows=true");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }


}
