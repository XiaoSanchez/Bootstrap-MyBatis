package com.qinghong;

import com.qinghong.entity.UUserInfo;
import com.qinghong.mapper.UUserInfoMapper;
import com.qinghong.mybatis.session.MySqlSession;
import com.qinghong.mybatis.session.MySqlSessionFactory;
import com.qinghong.mybatis.session.MySqlSessionFactoryBuilder;

import java.io.InputStream;

public class Test {
    public static void main(String[] args) {

        InputStream inputStream = Test.class.getClassLoader().getResourceAsStream("mybatis-config.xml");

        MySqlSessionFactory mySqlSessionFactory = new MySqlSessionFactoryBuilder().build(inputStream);

        MySqlSession mySqlSession = mySqlSessionFactory.openSession();

        UUserInfoMapper uUserInfoMapper = mySqlSession.getMappwe(UUserInfoMapper.class);

        UUserInfo uUserInfo = uUserInfoMapper.selectByPrimaryKey(1);

        System.out.println(uUserInfo.getId());
    }
}
